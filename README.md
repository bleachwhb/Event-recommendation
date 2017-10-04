TicketMaster_Project

Titan is event search and recommendation app + service to improve personal experience for event seekers. The front end includes HTML page and an iOS app to interact user. The backend includes a database and a java service to process data. At last, we deploy this service to Amazon Cloud and you can access everywhere.

API to implement:

Show events near your location (namely SearchEvents)
set favored events (namely ViewHistory)
Recommend new events (namely RecommendEvents)
HTTP request message: URL Request method: Get, Post, Delete Request body (optional) - content

HTTP response message: Status Code Response body

URL: identify the resource over the web protocal://hostname:port/path-and-file-name http://localhost:8080/Titan/history

Web application: Software application that user runs in the web browser. Web service: API runs on the server, which provides data to the client over http through a standardized messaging system (XML, JSON)

3-tier application architecture Presentation layer Logic layer Data layer

Servlet

Java servlet is a java program that extends the capabilities of a server. -> implement applications hosted on Web Servers.
Java servlet response to particular network request (HTTP request)
javax.servlet / javax.servlet.http
Tomcate Apache (servlet container) Tomcate Server, which is java servlet container and provides http web server environment in which java code could run. Servlets runs in container. Container handles the networking side (parsing http request and connection handling), mapping a URL to a particular servlet and ensuring that the URL requester has the correct access-rights.

Rest Architecture style for designing networked application, uses simple HTTP to make calls between machines.

Uniform Interface -> simplify and decouple
Stateless -> each request from client to server must contain all the information. Session state is entirely kept on the client.
Cacheable
Clent-server Benefit: decoupling, server = resources + operation
Titan:

Search for nearby [SearchItem], given lat, lon, return a list of object. a. Package: rpc b. Class name: SearchItem c. URL pattern: /search, http method: get
Recommend event for users, given an id and given a list of favorite events objects. URL pattern: /recommendation, http method: get
Set/Unset favorite events for user, given id and a list of favorite event ids, return status[OK or ERROR] URL pattern: /history, http method: post
JSON data-interchange format Key Value pair JSON only allow key names to be String JSON array: ["John": "Jack", "Mary": "Bob"]

The structure of our project

src/ rpc/ The entry point of our project, handles rpc request/response, parsing, etc. SearchItem.java RecommendItem.java ItemHistory.java RpcHelper.java Question: How do we design it? db/ The ‘backend’ of our project, connects to database. DBConnection.java is an interface. We have two implementations (MySQL vs. MongoDB) DbConnectionFactory.java is a factory class. mysql/ mongodb/ Question: How do we design it? externel/ Another ‘backend’ of our project, connects to public APIs. ExternalAPI.java is an interface. All supported backends should implement it. ExternalAPIFactory.java is a factory class. TicketMasterAPI.java. ‘Backend’ of our project, connects to TicketMaster API. Question: How do we design it? entity/ Handles creation/conversion/etc of object instances. Item.java algorithm/ Adds event recommendation algorithms. GeoRecommendation.java Recommendation.java

Factory Pattern

In Factory pattern, we create object without exposing the creation logic to client and the client use the same common interface to create new type of object. The idea is to use a static member-function (static factory method) which creates & returns instances, hiding the details of class modules from user.

Builder Pattern

Builder pattern builds a complex object using simple objects and using a step by step approach. It separates the construction of a complex object from its representation so that the same construction process can create different representations.

MAMP

MAMP is an integrated platform you can install on your machine which allows you to have access to a local MySQL server and its PHP server.

JDBC

JDBC provides interfaces and classes for writing database operations. It is a standard API that defines how Java programs access database management systems.

Singleton Pattern

The singleton pattern is a design pattern that restricts the instantiation of a class to one object.

public class MySQLConnection implements DBConnection { private static MySQLConnection instance;

public static DBConnection getInstance() {
	if (instance == null) {
		instance = new MySQLConnection();
	}
	return instance;
}
}

Sometimes we need to have only one instance of our class. We know that creating DB connections are costly. In Titan, a single DB connection is shared by multiple queries. There are other use cases. For example, there can be a single configuration manager or error manager in an application that handles all problems instead of creating multiple managers.

Recommendation System

Three components

User profile
Item profile
Review/History/User behavior -> like review, evaluation, comment ets.
Type:

Demographic-based Recommendation -> recommend items that users similar to you like.
Content-based Recommendation. -> recommend items that are similar to what you liked before.
Collaborative Filter

Titan is Content-based Recommendation System. In this project, we would like to recommend events based on categories that the user has visited.

Nosql and MongoDB distributed agile (去模式化) and schema less

Nosql Large volumes of rapidly changing structured, semi-structured, and unstructured data Agile sprints, quick schema iteration, and frequent code pushes Object-oriented programming that is easy to use and flexible Geographically distributed scale-out architecture instead of expensive, monolithic architecture

Nosql data type

Document each key with a complex data structure MongoDB -> json(key, value pair)
Graph
Nosql limits: Lack of efficient join operation Not suitable for business transaction application Consistent issue.

SQL MongoDB Table Collection Row	Documents Column Field join Not support
