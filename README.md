Just wanted to open source my last project on here

# We Play - Online Game Distribution Platform (Created by Gemini)

A full-stack e-commerce platform for digital game distribution, built with Java Spring Framework and modern web technologies. This project demonstrates a complete online game store similar to Steam, featuring user management, game cataloging, shopping cart functionality, and secure payment processing.

## 🎮 Features

### Core Functionality
- **User Management**: Registration, login, email verification, profile management
- **Game Catalog**: Browse games by category, tags, and search functionality
- **Shopping Experience**: Shopping cart, order processing, game activation codes
- **Admin Panel**: Game management, user oversight, content moderation
- **Responsive Design**: Mobile-friendly interface built with Bootstrap

### Technical Features
- **Authentication**: Token-based sessions with role-based access control
- **Security**: Spring interceptors, password encryption, email verification
- **Performance**: Redis caching, MyBatis ORM, database optimization
- **Monitoring**: AOP logging with AspectJ, comprehensive audit trails

## 🛠️ Technology Stack

### Backend
- **Java 19** - Core programming language
- **Spring Framework 5.3.14** - Application framework
- **Spring MVC** - Web framework
- **MyBatis 3.5.6** - ORM framework
- **MySQL 8.0** - Database
- **Redis** - Caching layer
- **AspectJ** - AOP framework

### Frontend
- **JSP** - Server-side templating
- **Bootstrap** - CSS framework
- **JavaScript** - Client-side functionality
- **JSTL** - JSP tag library

### Additional Tools
- **Log4j** - Logging framework
- **Jackson** - JSON processing
- **Commons Email** - Email functionality
- **Commons FileUpload** - File handling
- **Maven** - Build tool

## 🚀 Getting Started

### Prerequisites
- Java 19 or higher
- MySQL 8.0+
- Redis server
- Maven 3.6+
- Tomcat 9.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd shop
   ```

2. **Database Setup**
   ```bash
   # Create database and tables
   mysql -u root -p < src/main/resources/schema.sql
   
   # Import sample data (optional)
   mysql -u root -p shop < src/main/resources/dbdata.sql
   ```

3. **Configure Database**
   - Update `src/main/resources/jdbc.properties` with your database credentials
   - Ensure Redis server is running on default port (6379)

4. **Build and Deploy**
   ```bash
   mvn clean package
   # Deploy the generated WAR file to Tomcat
   ```

5. **Access the Application**
   - Open your browser and navigate to `http://localhost:8080/shop`
   - Default admin credentials may be available in the database

## 📁 Project Structure

```
shop/
├── src/main/java/cn/cie/
│   ├── controller/          # REST controllers
│   ├── entity/             # Data models
│   ├── services/           # Business logic
│   ├── mapper/             # Data access layer
│   ├── interceptor/        # Security interceptors
│   ├── utils/              # Utility classes
│   └── aop/                # Aspect-oriented programming
├── src/main/resources/
│   ├── mapper/             # MyBatis XML mappings
│   ├── spring-*.xml        # Spring configuration
│   └── mybatis-config.xml  # MyBatis configuration
├── src/main/webapp/
│   ├── WEB-INF/jsp/        # JSP views
│   ├── WEB-INF/css/        # Stylesheets
│   ├── WEB-INF/js/         # JavaScript files
│   └── WEB-INF/image/      # Game images and assets
└── pom.xml                 # Maven configuration
```

## 🔐 Security Features

- **Authentication**: Token-based session management
- **Authorization**: Role-based access control (User/Admin)
- **Input Validation**: Server-side validation and sanitization
- **Email Verification**: Required for new user accounts
- **Password Security**: Encrypted password storage

## 🎯 Key Components

### Controllers
- `GameController` - Game browsing and management
- `UserController` - User authentication and profiles
- `OrderController` - Shopping cart and orders
- `AdminController` - Administrative functions

### Services
- `UserService` - User management and validation
- `GameService` - Game catalog and operations
- `OrderService` - Order processing and management

### Database Schema
- **Users**: Authentication and profile data
- **Games**: Product catalog with metadata
- **Orders**: Shopping cart and purchase records
- **Categories**: Game classification system

## 🧪 Testing

Run the test suite using Maven:
```bash
mvn test
```

Test files are located in `src/test/java/cn/cie/` and cover:
- Service layer functionality
- Data access layer
- Utility classes
- Common functionality

## 📊 Performance Considerations

- Redis caching for frequently accessed data
- Database query optimization with MyBatis
- Efficient image handling and storage
- Responsive design for mobile devices

## 🔧 Configuration

### Database Configuration
Edit `src/main/resources/jdbc.properties`:
```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=utf8
jdbc.username=your_username
jdbc.password=your_password
```

### Redis Configuration
Ensure Redis server is running and accessible on the default port.

### Email Configuration
Configure email settings in the Spring configuration for user verification emails.

## 📝 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /game/{id}` - Game details
- `GET /user/register` - User registration
- `GET /user/login` - User login

### Protected Endpoints
- `POST /user/*` - User operations (requires login)
- `POST /order/*` - Order operations (requires login)
- `GET /admin/*` - Admin operations (requires admin role)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Development Team** - *Initial work* - [Derek Sun]
