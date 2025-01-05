# RoadMap

## MVP Features

1. User Registration/Login

- Register via email or Google/Facebook.
- Store basic user information (name, email, location).

2. Post Donation Items

- Form includes:
  - Title.
  - Description.
  - Category (clothes, books, electronics, etc.).
  - Location (HCMC, Hanoi, or select on map).
  - Item condition (new, used).
  - Upload 3 - 5 images.

3. Search for Items by Distance

- Search filters:
  - Most recent.
  - Keyword.
  - Location (distance from HCMC or Hanoi).
  - Item category.
  - Display items on a map, showing proximity to the user.

4. View Post Details

- Thumbnail images.
- Show full details of the item.
- Contact information.

## System Architecture

1. Frontend:

- Next.js for building the interface.
- TailwindCSS for styling.
- AWS Amplify for hosting.

2. Backend:

- API Gateway: Handles requests from the frontend.
- AWS Lambda: Processes business logic.
- DynamoDB: Single table for storing users and posts.
- S3: Stores images of donated items.
- Cognito: Manages authentication and user accounts.

## Data Flow

1. User Registration/Login

- User inputs credentials → Frontend sends request to API Gateway → Lambda processes request → Cognito handles authentication → User data stored in Users table.

2. Posting an Item

- User fills out the form and selects a location → Frontend sends request to API Gateway → Lambda saves data to Posts table → Images uploaded to S3.

3. Search Items by Distance

- User enters their location → Frontend sends coordinates to backend → Lambda calculates distances between user and items using Haversine formula → Returns nearby items.

4. View Item Details

- Frontend sends request for item details → Lambda retrieves data from Posts table → Returns full item information.

## User Interface (UI)

1. Homepage

- Display a list of items based on user’s location.
- Search bar and filters at the top.

2. Registration/Login Page

- Simple form for user credentials.

3. Create Post Page

- Form to input item details and select location on the map.

4. Item Details Page

- Show item information and a map with the item’s location.

## Map Integration

- Use Google Maps API to:
  - Display a map on the frontend.
  - Show item locations and user’s current position.
  - Provide directions if needed.


## DynamoDB Design

- Single table for users and posts.
- TBU