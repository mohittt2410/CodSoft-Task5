import java.io.*;
import java.util.*;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(contacts);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            contacts = (List<Contact>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class AddressBookSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        while (true) {
            System.out.println("-----Address Book System-----");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Save Contacts to File");
            System.out.println("6. Load Contacts from File");
            System.out.println("7. Exit");

            System.out.print("Select an Option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter Name : ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone Number : ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter Email Address : ");
                    String emailAddress = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber, emailAddress);
                    addressBook.addContact(newContact);
                    System.out.println("Contact Addedd Successfully...");
                    break;

                case 2:
                    System.out.print("Enter the Name of the Contact to Remove : ");
                    String contactName = scanner.nextLine();
                    Contact contactToRemove = addressBook.searchContact(contactName);
                    if (contactToRemove != null) {
                        addressBook.removeContact(contactToRemove);
                        System.out.println("Contact Removed Successfully...");
                    } else {
                        System.out.println("Contact not found...");
                    }
                    break;

                case 3:
                    System.out.print("Enter the Name of the Contact to Search : ");
                    String searchName = scanner.nextLine();
                    Contact foundContact = addressBook.searchContact(searchName);
                    if (foundContact != null) {
                        System.out.println("Contact found : " + foundContact);
                    } else {
                        System.out.println("Contact not found...");
                    }
                    break;

                case 4:
                    List<Contact> allContacts = addressBook.getAllContacts();
                    if (!allContacts.isEmpty()) {
                        System.out.println("All Contacts : ");
                        for (Contact contact : allContacts) {
                            System.out.println(contact);
                        }
                    } else {
                        System.out.println("No contacts found...");
                    }
                    break;

                case 5:
                    addressBook.saveToFile("contacts.dat");
                    break;

                case 6:
                    addressBook.loadFromFile("contacts.dat");
                    break;

                case 7:
                    System.out.println("Exiting the Address Book System.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}