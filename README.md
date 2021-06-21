## Scratch bling api project

This project was built with spring boot and is continuously deployed to Azure App service through Github actions when a push happens in the main branch of the repo.



#### Deliverables
- The app is deployed under the url: https://scratch-bling-app.azurewebsites.net

- **Consumer web interface:** https://scratch-bling-app.azurewebsites.net/web:
     
  - This web interface allows anyone to see the list of available scratchers.

- **Admin panel:** https://scratch-bling-app.azurewebsites.net/web/admin:
    
  - The credentials for the admin panel are user: _admin_ password: _bling182_
  
- **API endpoint:** https://scratch-bling-app.azurewebsites.net/api:
  
    - The API endpoint supports the standard GET, POST, PUT, PATCH, DELETE operations.
      
    - The endpoint for GET `(/items, /items/{id}) `are open to be consumed by anyone.
      
    - The endpoints that change state of the application  `(POST /items, PATCH /items/{id}, PUT /items/{id}, DELETE /items/{id}` are protected, and a JWT token is need for any request to be accepted in the Bearer authentication scheme.
    
    - To generate the token a call to `POST /auth` needs to be made with valid credentials.
  
- **Postman collection:** [/deliverables/postman-exports/Scratch-bling.postman_collection.json](/deliverables/postman-exports/Scratch-bling.postman_collection.json):
  
  - Note that alongside the collection there is an environment _[/deliverables/postman-exports/sb-ENV.postman_environment.json](/deliverables/postman-exports/sb-ENV.postman_environment.json)_ that needs to be imported and selected in order to provide values to some variables in the collection.
  
  - Because a token is needed to make some API requests, a call to the `/auth` endpoint is needed to generate the token, that call is already configured within the collection and also sets the token for the needed endpoints.
  
  - A set of screenshots of the postman API calls are available:
  
  - Get all items `GET /items`:
  ![Get all items call](/deliverables/postman-exports/get-all-items.png)
    
  - Get item by id `GET /items/{id}`:
  ![Get item by id](/deliverables/postman-exports/get-item-by-id.png)
    
  - Create Item `POST /items`:
  ![Create item](/deliverables/postman-exports/POST-item.png)
    
  - Update (full) item by id `PUT /items/{id}`:
  ![Update item by Id](/deliverables/postman-exports/update-by-id.png)
    
  - Update (partial) item by id `PATCH /items/{id}`:
  ![Partial update (PATCH)](/deliverables/postman-exports/PATCH-by-id.png)
    
  - Delete item by id `DELETE /items/{id}`:
  ![Delete item by id](/deliverables/postman-exports/DELETE-by-id.png)
    
  - Calling the auth endpoint `POST /auth`:
  ![Generate token](/deliverables/postman-exports/auth.png)
    
- The web interface at `/web` or `/web/admin` and the API endpoint `GET /items` support paging and sorting, in order to page and/or sort query parameters need to be supplied in the form: `?page={number}&size={number}&sort={fieldname}`. By default the page size is 20.