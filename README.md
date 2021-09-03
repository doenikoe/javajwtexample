# javajwtexample
This an example of java springboot application with JWT implementation. This is intended for learning purpose, therefor some crucial security features might be missing.

# Running app locally
This app running on port 4300

# Available endpoint
## [POST] /user/token
Generate JWT token for respective user

Sample request
```
curl -X POST -H "Content-Type: application/json" \
    -d '{"id": 1}' \
    http://localhost:4300/user/token
```

## [GET] /user/get/{id}
Get user by id, this endpoint requires 'Authorization' Header with Bearer token

Sample request
```
curl GET -H "Authorization: Bearer mytoken123" \
    http://localhost:4300/user/get/1
```

## [POST] /user
Create new user

Sample request
```
curl -X POST -H "Content-Type: application/json" \
    -d '{"name": "John Doe", "phone": "021123456"}' \
    http://localhost:4300/user
```

## [PUT] /user/update/{id}
Update user by id, this endpoint requires 'Authorization' Header with Bearer token

Sample request
```
curl -X PUT -H "Content-Type: application/json" \
    -H "Authorization: Bearer mytoken123" \
    -d '{"name": "Another John Doe", "phone": "021123456"}' \
    http://localhost:4300/user/update/1
```

## [DELETE] /user/delete/{id}
Delete user by id, this endpoint requires 'Authorization' Header with Bearer token

Sample request
```
curl -X DELETE -H "Authorization: Bearer mytoken123" \
    http://localhost:4300/user/delete/1
```


