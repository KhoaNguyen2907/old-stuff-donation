# DynamoDB Single-Table Design

## Table Name: OldStuffDonation

### Primary Keys
- **Partition Key (PK)**: String
- **Sort Key (SK)**: String

### Access Patterns
1. Get user profile -> Index: Main Table | PK: USER#<cognitoId> | SK: PROFILE
2. Get all posts by user -> Index: USER-GSI | PK: USER#<cognitoId> | SK begins_with POST#
3. Get post by ID -> Index: Main Table | PK: POST#<ksuid> | SK: METADATA
4. Get posts by location -> Index: GEO-GSI | PK: GEO#<geohash> | SK begins_with POST#
5. Get posts by category -> Index: CAT-GSI | PK: CAT#<category> | SK begins_with POST#
6. Get recent posts -> Index: Main Table | PK begins_with POST# | SK: METADATA

### Item Types and Key Patterns

#### 1. User Profile
```
PK: USER#<cognitoId>
SK: PROFILE
Attributes:
- name (String)
- phone (String)
- email (String)  // From Cognito
- profilePicture (String)  // S3 URL
- location (String)
- createdAt (Number)
- stats (Map)
  - totalDonations (Number)
  - activeDonations (Number)
  - completedDonations (Number)
```

#### 2. Post
```
PK: POST#<ksuid>
SK: METADATA
Attributes:
- title (String)
- description (String)
- category (String)
- condition (String) - "NEW" | "USED"
- addressString (String)
- location (Map)
  - latitude (Number)
  - longitude (Number)
  - city (String)
  - geohash (String)
- imageUrls (List<String>)
- donorId (String)  // Cognito User ID
- status (String) - "AVAILABLE" | "CLAIMED" | "COMPLETED"
- USER-GSI-PK: USER#<cognitoId>
- USER-GSI-SK: POST#<ksuid>
- GEO-GSI-PK: GEO#<geohash>
- GEO-GSI-SK: POST#<ksuid>
- CAT-GSI-PK: CAT#<category>
- CAT-GSI-SK: POST#<ksuid>
```

#### 3. Post Contact
```
PK: POST#<ksuid>
SK: CONTACT
Attributes:
- name (String)
- email (String)
- phone (String)
- address (String)
```

### Global Secondary Indexes (GSIs)

#### 1. USER-GSI (User Posts)
- **PK**: USER-GSI-PK (USER#<cognitoId>)
- **SK**: USER-GSI-SK (POST#<ksuid>)
Purpose: Get all posts by a user

#### 2. GEO-GSI (Location Access)
- **PK**: GEO-GSI-PK (GEO#<geohash>)
- **SK**: GEO-GSI-SK (POST#<ksuid>)
Purpose: Get posts by location

#### 3. CAT-GSI (Category Access)
- **PK**: CAT-GSI-PK (CAT#<category>)
- **SK**: CAT-GSI-SK (POST#<ksuid>)
Purpose: Get posts by category

### Example Queries

1. Get User Profile:
```javascript
PK = "USER#cognito-user-id"
SK = "PROFILE"
```

2. Get User's Posts:
```javascript
// Using USER-GSI
USER-GSI-PK = "USER#cognito-user-id"
USER-GSI-SK begins_with "POST#"
```

3. Get Posts by Location:
```javascript
// Using GEO-GSI
GEO-GSI-PK = "GEO#<geohash>"
GEO-GSI-SK begins_with "POST#"
```

4. Get Posts by Category:
```javascript
// Using CAT-GSI
CAT-GSI-PK = "CAT#electronics"
CAT-GSI-SK begins_with "POST#"
```

5. Get Recent Posts:
```javascript
// Using main table, KSUID is time sortable
PK begins_with "POST#"
SK = "METADATA"
```

### Notes

1. **Authentication**:
   - Use AWS Cognito for all auth
   - Social providers configured in Cognito
   - JWT tokens contain Cognito User ID
   - No need to manage auth in DynamoDB

2. **KSUID Benefits**:
   - Time sortable for posts
   - No need for separate createdAt field
   - Globally unique
   - URL-safe

3. **Geohash Implementation**:
   - Use 6-character geohash for ~1.2km precision
   - Query adjacent geohashes for radius search
   - Stored in post attributes for easy updates

4. **Image Storage**:
   - Store S3 URLs in imageUrls attribute
   - Consider thumbnail URLs for listing views

5. **Pagination**:
   - Use LastEvaluatedKey for pagination
   - KSUID provides natural time-based ordering 