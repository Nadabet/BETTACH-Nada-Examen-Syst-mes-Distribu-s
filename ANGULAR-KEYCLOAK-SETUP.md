# Keycloak Configuration for Angular Frontend

## Prerequisites
Make sure Keycloak is running on http://localhost:8080

## Steps to Configure Keycloak Client

### 1. Access Keycloak Admin Console
- URL: http://localhost:8080
- Username: admin
- Password: admin

### 2. Create Client for Angular Application

1. Navigate to **Clients** in the left menu
2. Click **Create client** button
3. Fill in the following details:

**General Settings:**
- Client type: `OpenID Connect`
- Client ID: `angular-client`
- Click **Next**

**Capability config:**
- Client authentication: `OFF` (Public client)
- Authorization: `OFF`
- Authentication flow:
  - ✅ Standard flow
  - ✅ Direct access grants
- Click **Next**

**Login settings:**
- Root URL: `http://localhost:4200`
- Home URL: `http://localhost:4200`
- Valid redirect URIs: 
  - `http://localhost:4200/*`
- Valid post logout redirect URIs:
  - `http://localhost:4200/*`
- Web origins: 
  - `http://localhost:4200`
  - `+` (to allow CORS from valid redirect URIs)

4. Click **Save**

### 3. Create Realm Roles

1. Go to **Realm roles** in the left menu
2. Click **Create role**
3. Create the following roles:
   - Role name: `USER`
   - Click **Save**
4. Repeat for:
   - Role name: `ADMIN`

### 4. Create Users

1. Go to **Users** in the left menu
2. Click **Add user**

**User 1 (Regular User):**
- Username: `user`
- Email: `user@example.com`
- Email verified: `ON`
- First name: `Test`
- Last name: `User`
- Click **Create**

Then:
- Go to **Credentials** tab
- Click **Set password**
- Password: `user`
- Temporary: `OFF`
- Click **Save**

Go to **Role mapping** tab:
- Click **Assign role**
- Filter by realm roles
- Select `USER`
- Click **Assign**

**User 2 (Admin User):**
- Username: `admin`
- Email: `admin@example.com`
- Email verified: `ON`
- First name: `Admin`
- Last name: `User`
- Click **Create**

Then:
- Go to **Credentials** tab
- Click **Set password**
- Password: `admin`
- Temporary: `OFF`
- Click **Save**

Go to **Role mapping** tab:
- Click **Assign role**
- Filter by realm roles
- Select both `USER` and `ADMIN`
- Click **Assign**

### 5. Verify Configuration

The Angular app is now configured to use:
- **Keycloak URL:** http://localhost:8080
- **Realm:** master
- **Client ID:** angular-client

### 6. Test the Application

1. Start the Angular app: `npm start` (if not already running)
2. Open http://localhost:4200
3. Click **Login** button in the navbar
4. You will be redirected to Keycloak login page
5. Login with:
   - Username: `user` or `admin`
   - Password: `user` or `admin`
6. After successful login, you'll be redirected back to the app
7. The username will appear in the navbar
8. You can now access the protected APIs

### Troubleshooting

**If you see CORS errors:**
- Make sure Web origins is set to `http://localhost:4200` or `+`

**If login doesn't work:**
- Verify the client ID matches: `angular-client`
- Check that Valid redirect URIs includes `http://localhost:4200/*`
- Make sure Client authentication is `OFF` (public client)

**If APIs return 401:**
- Check that users have the correct roles (USER or ADMIN)
- Verify the JWT token is being sent in the Authorization header
