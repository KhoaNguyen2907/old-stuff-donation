
## Test Data

### Donation Request

```bash
curl https://{api-id}.execute-api.{region}.amazonaws.com/dev/api/donations -d '{
    "itemName": "Old Laptop",
    "description": "2019 MacBook Pro",
    "donorName": "John Doe",
    "contactInfo": "john@example.com"
}'
```

```bash
curl -X POST \
  http://localhost:8080/donationFunction \
  -H 'Content-Type: application/json' \
  -d '{
    "body": "{\"itemName\":\"Old Laptop\",\"description\":\"2019 MacBook Pro\",\"donorName\":\"John Doe\",\"contactInfo\":\"john@example.com\"}",
    "headers": {
        "Content-Type": "application/json"
    },
    "httpMethod": "POST",
    "path": "/api/donations"
}'
```
### Donation Response

```bash
{"message":"Donation request processed successfully","itemName":"Old Laptop","donorName":"John Doe","status":"ACCEPTED"}
```

