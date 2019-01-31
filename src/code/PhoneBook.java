package code;

import java.util.*;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mai (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

  /*
   * ADD MORE FIELDS IF NEEDED
   * 
   */

	BinarySearchTree<String, ContactInfo> nameTree = new BinarySearchTree<String, ContactInfo>();
	BinarySearchTree<String, ContactInfo> numberTree = new BinarySearchTree<String, ContactInfo>();
	
 	
  public PhoneBook() {
    nameTree.setComparator(new DefaultComparator<String>());
    numberTree.setComparator(new DefaultComparator<String>());
  }

  // Returns the number of contacts in the phone book
  public int size() {
    return nameTree.size();
  }

  // Returns true is the phone book is empty, false otherwise
  public boolean isEmpty() {
    return numberTree.isEmpty();
  }

//Adds a new contact overwrites an existing contact with the given info
  // Args should be given in the order of e-mail and address which is handled for
  // you
  // Note that it can also be empty. If you do not want to update a field pass
  // null
  public void addContact(String name, String number, String... args) {
    int numArgs = args.length;
    String email = null;
    String address = null;

    ContactInfo addedCont = new ContactInfo(name, number);

    /*
     * Add stuff here if needed
     */

    if (numArgs > 0)
      if (args[0] != null)
        email = args[0];
    if (numArgs > 1)
      if (args[1] != null)
        address = args[1];
    if (numArgs > 2)
      System.out.println("Ignoring extra arguments");
    
    if(email != null) {
		addedCont.setEmail(email);
    }
    	if(address != null) {
		addedCont.setAddress(address);
    	}
    	
    	nameTree.put(name, addedCont);
    	numberTree.put(number, addedCont);
    	
    	//CANNOT WORK ON IT ANYMORE//
    
    	 
  }

  // Return the contact info with the given name
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByName(String name) {
    BinaryTreeNode<String, ContactInfo> node = nameTree.getNode(name);
    if(node.getValue().getName() == null || node == null) return null;
    return node.getValue();
    
  }

  // Return the contact info with the given phone number
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByPhone(String phoneNumber) {
	 BinaryTreeNode<String, ContactInfo> node = numberTree.getNode(phoneNumber); 
	 if(node.getValue().getNumber() == null || node == null) return null;
	 return node.getValue();
  }

  // Return the contact info with the given e-mail
  // Return null if it does not exists
  // Can be O(n)
  public ContactInfo searchByEmail(String email) {
    List<BinaryTreeNode<String, ContactInfo>> eList = nameTree.getNodesInOrder();
    ContactInfo cont2BeRet = null;
    for(BinaryTreeNode<String, ContactInfo> node : eList) {
    		if(node.getValue().getEmail() != null) {
	    		if(node.getValue().getEmail().equals(email)) {
	    			cont2BeRet = node.getValue();
	    		}
    		}
    }
    return cont2BeRet;
  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given name to delete, false otherwise
  public boolean removeByName(String name) {
	BinaryTreeNode<String, ContactInfo> node = nameTree.getNode(name);
	String nodeNum = node.getValue().getNumber();
    if(node.getValue() == null) return false;
    numberTree.remove(nodeNum);
    nameTree.remove(name);
    return true;
  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given number to delete, false otherwise
  public boolean removeByNumber(String phoneNumber) {
	  BinaryTreeNode<String, ContactInfo> node = numberTree.getNode(phoneNumber);
	  String nodeName = node.getValue().getName();
	  if(node.getValue() == null) return false;
	  nameTree.remove(nodeName);
	  numberTree.remove(phoneNumber);
	  return true;
  }

  // Returns the number associated with the name
  public String getNumber(String name) {
    ContactInfo cont = searchByName(name);
    return cont.getNumber();
  }

  // Returns the name associated with the number
  public String getName(String number) {
	  ContactInfo cont = searchByPhone(number);
	  return cont.getName();
  }
  
  // Update the email of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateEmail(String name, String email) {
    ContactInfo cont = searchByName(name);
    if(cont == null) return false;
    cont.setEmail(email);
    return true;
  }
  
  // Update the address of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateAddress(String name, String address) {
	  ContactInfo cont = searchByName(name);
	    if(cont == null) return false;
	    cont.setAddress(address);
	    return true;
  }

  // Returns a list containing the contacts in sorted order by name
  public List<ContactInfo> getContacts() {
    List<BinaryTreeNode<String, ContactInfo>> nList = nameTree.getNodesInOrder();
    List<ContactInfo> cList = new ArrayList<ContactInfo>();
    for(BinaryTreeNode<String, ContactInfo> node : nList) {
    		if(node.getValue() != null) {
    			cList.add(node.getValue());
    		}
    }
    return cList;
  }

  // Prints the contacts in sorted order by name
  public void printContacts() {
    List<ContactInfo> printList = getContacts();
    for(ContactInfo cont : printList) {
    		System.out.println(cont);
    }
  }
}
