import java.util.ArrayList;
import java.util.Scanner;

abstract class User {
    protected String username;
    protected String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public abstract void viewObjectsOfInterest(Institution institution);
}

class Administrator extends User {
    private Institution institution;

    public Administrator(String username, String password, Institution institution) {
        super(username, password);
        this.institution = institution;
    }

    public void approveClient(Client client) {
        client.setApproved(true);
        System.out.println("Administrator approved client: " + client.getUsername());
    }

    public void processServiceRequest(ServiceRequest request) {
        request.processRequest();
        System.out.println("Administrator processed service request: " + request.getRequestType());
    }

    public void addAuctionHouse(AuctionHouse auctionHouse) {
        institution.addAuctionHouse(auctionHouse);
    }

    @Override
    public void viewObjectsOfInterest(Institution institution) {
        institution.displayObjects();
    }
}

class Expert extends User {
    private ArrayList<Schedule> availability = new ArrayList<>();

    public Expert(String username, String password) {
        super(username, password);
    }
    
    public void setAvailability(Schedule schedule) {
        availability.add(schedule);
        System.out.println("Expert set availability: " + schedule);
    }
    
    public void offerService(Client client) {
        System.out.println("Expert offering service to client: " + client.getUsername());
    }
    
    @Override
    public void viewObjectsOfInterest(Institution institution) {
        System.out.println("Expert viewing objects of interest:");
        institution.displayObjects();
    }
}

class Client extends User {
    private boolean isApproved;

    public Client(String username, String password) {
        super(username, password);
        this.isApproved = false;
    }
    
    public boolean isApproved() {
        return isApproved;
    }
    
    public void requestService(ServiceRequest request) {
        System.out.println("Client requested service: " + request.getRequestType());
    }
    
    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }
    
    @Override
    public void viewObjectsOfInterest(Institution institution) {
        System.out.println("Client viewing objects of interest:");
        institution.displayObjects();
    }
}

class ObjectOfInterest {
    private String name;
    private String type;
    private boolean ownedByInstitution;
    
    public ObjectOfInterest(String name, String type, boolean ownedByInstitution) {
        this.name = name;
        this.type = type;
        this.ownedByInstitution = ownedByInstitution;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isOwnedByInstitution() {
        return ownedByInstitution;
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ") - " + (ownedByInstitution ? "Owned by Institution" : "Not Owned");
    }
}

class Institution {
    private ArrayList<ObjectOfInterest> objects = new ArrayList<>();
    private ArrayList<AuctionHouse> auctionHouses = new ArrayList<>();
    
    public void addObject(ObjectOfInterest object) {
        objects.add(object);
    }
    
    public void addAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.add(auctionHouse);
    }
    
    public void displayObjects() {
        if (objects.isEmpty()) {
            System.out.println("No objects of interest available.");
        } else {
            for (ObjectOfInterest obj : objects) {
                System.out.println(obj);
            }
        }
    }
}

class AuctionHouse {
    private String name;
    private City location;

    public AuctionHouse(String name, City location) {
        this.name = name;
        this.location = location;
    }
    
    public String getName() {
        return name;
    }
    
    public City getLocation() {
        return location;
    }
}

class City {
    private String name;
    private String country;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCountry() {
        return country;
    }
}

class ServiceRequest {
    private String requestType;
    private String status;
    
    public ServiceRequest(String requestType) {
        this.requestType = requestType;
        this.status = "Pending";
    }
    
    public void processRequest() {
        this.status = "Processed";
    }
    
    public String getRequestType() {
        return requestType;
    }
}

class Schedule {
    private String timeSlot;
    
    public Schedule() {
        this.timeSlot = "Default Schedule";
    }
    
    @Override
    public String toString() {
        return timeSlot;
    }
}

class InMemoryDatabase {
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<AuctionHouse> auctionHouses = new ArrayList<>();
    private ArrayList<ObjectOfInterest> objects = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();
    
    public void storeCity(City city) {
        cities.add(city);
    }
    
    public void storeAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.add(auctionHouse);
    }
    
    public void storeObject(ObjectOfInterest object) {
        objects.add(object);
    }
    
    public void storeUser(User user) {
        users.add(user);
    }
    
    public void storeServiceRequest(ServiceRequest request) {
        serviceRequests.add(request);
    }
    
    public ArrayList<City> getAllCities() {
        return cities;
    }
    
    public ArrayList<AuctionHouse> getAllAuctionHouses() {
        return auctionHouses;
    }
    
    public ArrayList<ObjectOfInterest> getAllObjects() {
        return objects;
    }
    
    public ArrayList<User> getAllUsers() {
        return users;
    }
    
    public ArrayList<ServiceRequest> getAllServiceRequests() {
        return serviceRequests;
    }
    
    public ArrayList<ObjectOfInterest> getObjectsByType(String type) {
        ArrayList<ObjectOfInterest> result = new ArrayList<>();
        for (ObjectOfInterest obj : objects) {
            if (obj.getType().equals(type)) {
                result.add(obj);
            }
        }
        return result;
    }
    
    public ArrayList<AuctionHouse> getAuctionHousesByCity(String cityName) {
        ArrayList<AuctionHouse> result = new ArrayList<>();
        for (AuctionHouse ah : auctionHouses) {
            if (ah.getLocation().getName().equals(cityName)) {
                result.add(ah);
            }
        }
        return result;
    }
    
    public ArrayList<Client> getApprovedClients() {
        ArrayList<Client> result = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Client) {
                Client client = (Client) user;
                if (client.isApproved()) {
                    result.add(client);
                }
            }
        }
        return result;
    }
    
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                break;
            }
        }
    }
    
    public void deleteServiceRequest(ServiceRequest request) {
        serviceRequests.remove(request);
    }
}

class ArtAdvisorySystem {
    private static InMemoryDatabase db;
    private static Institution institution;
    private static Scanner scanner;
    private static User currentUser;

    public static void main(String[] args) {
        System.out.println("\n=== Art Advisory System ===");
        initializeSystem();
        scanner = new Scanner(System.in);

        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void initializeSystem() {
        db = new InMemoryDatabase();
        institution = new Institution();
        
        // Initialize sample data
        initializeCities();
        initializeAuctionHouses();
        initializeObjects();
        initializeUsers();
        initializeServiceRequests();
    }

    private static void initializeCities() {
        db.storeCity(new City("London", "UK"));
        db.storeCity(new City("Paris", "France"));
        db.storeCity(new City("New York", "USA"));
    }

    private static void initializeAuctionHouses() {
        City london = db.getAllCities().get(0);
        City paris = db.getAllCities().get(1);
        City newYork = db.getAllCities().get(2);

        db.storeAuctionHouse(new AuctionHouse("Christie's", london));
        db.storeAuctionHouse(new AuctionHouse("Sotheby's", newYork));
        db.storeAuctionHouse(new AuctionHouse("Artcurial", paris));
    }

    private static void initializeObjects() {
        ObjectOfInterest monaLisa = new ObjectOfInterest("Mona Lisa", "Painting", true);
        ObjectOfInterest venus = new ObjectOfInterest("Venus de Milo", "Sculpture", true);
        ObjectOfInterest modernArt = new ObjectOfInterest("Modern Art Installation", "Installation", false);
        ObjectOfInterest egyptianVase = new ObjectOfInterest("Ancient Egyptian Vase", "Artifact", true);
        ObjectOfInterest photography = new ObjectOfInterest("Contemporary Photography Collection", "Photography", false);

        // Store in database
        db.storeObject(monaLisa);
        db.storeObject(venus);
        db.storeObject(modernArt);
        db.storeObject(egyptianVase);
        db.storeObject(photography);

        // Add to institution
        institution.addObject(monaLisa);
        institution.addObject(venus);
        institution.addObject(modernArt);
        institution.addObject(egyptianVase);
        institution.addObject(photography);
    }

    private static void initializeUsers() {
        Administrator admin = new Administrator("admin", "admin123", institution);
        Expert expert1 = new Expert("expert1", "expert123");
        Expert expert2 = new Expert("expert2", "expert456");
        Client client1 = new Client("client1", "client123");
        Client client2 = new Client("client2", "client456");

        // Store in database
        db.storeUser(admin);
        db.storeUser(expert1);
        db.storeUser(expert2);
        db.storeUser(client1);
        db.storeUser(client2);

        // Initialize client status
        client1.setApproved(false);
        client2.setApproved(false);
    }

    private static void initializeServiceRequests() {
        db.storeServiceRequest(new ServiceRequest("Painting Authentication"));
        db.storeServiceRequest(new ServiceRequest("Art Valuation"));
        db.storeServiceRequest(new ServiceRequest("Collection Management"));
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                System.out.println("Thank you for using Art Advisory System!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : db.getAllUsers()) {
            if (user.authenticate(username, password)) {
                currentUser = user;
                System.out.println("Login successful! Welcome, " + username);
                return;
            }
        }
        System.out.println("Invalid credentials. Please try again.");
    }

    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        if (currentUser instanceof Administrator) {
            showAdminMenu();
        } else if (currentUser instanceof Expert) {
            showExpertMenu();
        } else if (currentUser instanceof Client) {
            showClientMenu();
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. View All Objects");
        System.out.println("2. View All Auction Houses");
        System.out.println("3. View All Users");
        System.out.println("4. Approve Client");
        System.out.println("5. Process Service Request");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("\nAll Objects:");
                institution.displayObjects();
                break;
            case 2:
                System.out.println("\nAll Auction Houses:");
                db.getAllAuctionHouses().forEach(ah -> 
                    System.out.println(ah.getName() + " in " + ah.getLocation().getName()));
                break;
            case 3:
                System.out.println("\nAll Users:");
                db.getAllUsers().forEach(user -> 
                    System.out.println(user.getClass().getSimpleName() + ": " + user.getUsername()));
                break;
            case 4:
                approveClient();
                break;
            case 5:
                processServiceRequest();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void showExpertMenu() {
        System.out.println("\n=== Expert Menu ===");
        System.out.println("1. View Objects");
        System.out.println("2. Set Availability");
        System.out.println("3. View Service Requests");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("\nAvailable Objects:");
                institution.displayObjects();
                break;
            case 2:
                System.out.println("\nSetting Availability:");
                Schedule schedule = new Schedule();
                ((Expert)currentUser).setAvailability(schedule);
                System.out.println("Availability set successfully!");
                break;
            case 3:
                System.out.println("\nService Requests:");
                ArrayList<ServiceRequest> requests = db.getAllServiceRequests();
                if (requests.isEmpty()) {
                    System.out.println("No service requests available.");
                } else {
                    requests.forEach(req -> 
                        System.out.println("- " + req.getRequestType()));
                }
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void showClientMenu() {
        System.out.println("\n=== Client Menu ===");
        System.out.println("1. View Objects");
        System.out.println("2. Request Service");
        System.out.println("3. View My Status");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("\nAvailable Objects:");
                institution.displayObjects();
                break;
            case 2:
                requestService();
                break;
            case 3:
                if (currentUser instanceof Client) {
                    Client client = (Client)currentUser;
                    System.out.println("\nYour Status:");
                    System.out.println("Username: " + client.getUsername());
                    System.out.println("Approval status: " + 
                        (client.isApproved() ? "Approved" : "Pending"));
                }
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void approveClient() {
        System.out.println("\nPending Clients:");
        ArrayList<Client> pendingClients = new ArrayList<>();
        for (User user : db.getAllUsers()) {
            if (user instanceof Client) {
                Client client = (Client)user;
                if (!client.isApproved()) {
                    pendingClients.add(client);
                    System.out.println("- " + client.getUsername());
                }
            }
        }

        if (pendingClients.isEmpty()) {
            System.out.println("No pending clients.");
            return;
        }

        System.out.print("Enter username to approve: ");
        String username = scanner.nextLine();

        for (Client client : pendingClients) {
            if (client.getUsername().equals(username)) {
                ((Administrator)currentUser).approveClient(client);
                db.updateUser(client);
                System.out.println("Client approved successfully!");
                return;
            }
        }
        System.out.println("Client not found.");
    }

    private static void processServiceRequest() {
        System.out.println("\nPending Service Requests:");
        ArrayList<ServiceRequest> requests = db.getAllServiceRequests();
        
        if (requests.isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }

        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i+1) + ". " + requests.get(i).getRequestType());
        }

        System.out.print("Enter request number to process: ");
        int requestNum = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (requestNum > 0 && requestNum <= requests.size()) {
            ServiceRequest request = requests.get(requestNum-1);
            ((Administrator)currentUser).processServiceRequest(request);
            db.deleteServiceRequest(request);
            System.out.println("Service request processed successfully!");
        } else {
            System.out.println("Invalid request number.");
        }
    }

    private static void requestService() {
        System.out.println("\nAvailable Services:");
        System.out.println("1. Painting Authentication");
        System.out.println("2. Art Valuation");
        System.out.println("3. Collection Management");
        System.out.print("Choose a service: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String serviceType;
        switch (choice) {
            case 1:
                serviceType = "Painting Authentication";
                break;
            case 2:
                serviceType = "Art Valuation";
                break;
            case 3:
                serviceType = "Collection Management";
                break;
            default:
                System.out.println("Invalid service type.");
                return;
        }

        ServiceRequest request = new ServiceRequest(serviceType);
        ((Client)currentUser).requestService(request);
        db.storeServiceRequest(request);
        System.out.println("Service request submitted successfully!");
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }
}
