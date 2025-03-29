import java.util.ArrayList;

abstract class User {
    protected String username;
    protected String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
    
    public void requestService(ServiceRequest request) {
        System.out.println("Client requested service: " + request.getRequestType());
    }
    
    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }
    
    public String getUsername() {
        return username;
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
}

class City {
    private String name;
    private String country;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
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

public class ArtAdvisorySystem {
    public static void main(String[] args) {
        // Create institution and city
        Institution institution = new Institution();
        City london = new City("London", "UK");
        AuctionHouse christies = new AuctionHouse("Christie's", london);
        
        // Hardcoded users
        Administrator admin = new Administrator("admin", "admin123", institution);
        Expert expert = new Expert("expert1", "expert123");
        Client client = new Client("client1", "client123");
        
        // Admin adds auction house
        admin.addAuctionHouse(christies);

        // Admin approves client
        admin.approveClient(client);
        
        // Create a service request
        ServiceRequest serviceRequest = new ServiceRequest("Consultation on painting");
        client.requestService(serviceRequest);
        admin.processServiceRequest(serviceRequest);
        
        // Users view objects
        System.out.println("\n--- Viewing as Expert ---");
        expert.viewObjectsOfInterest(institution);
        
        System.out.println("\n--- Viewing as Client ---");
        client.viewObjectsOfInterest(institution);
    }
}
