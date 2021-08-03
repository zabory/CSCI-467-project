package Controllers.API;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Controllers.AdminPageController;
import Controllers.CheckoutController;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
import Database.Records.OrderRecord;
import Database.Records.PartRecord;
import application.App;

/**
 * This class is strictly for learning purposes. It has nothing(everything) to do with the actual project.
 * This class contains a true restful API integration with the database and its functions
 * @author Ben Shabowski, Samuel Haffner, Leo Jaos,  Alicia E
 *
 */
@Controller
public class RestfulAPIController {

	private DatabaseInterfacer DBInterfacer;

	private enum RecordType {
		Part, Customer, Order
	}

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	/**
	 * Sends entire list of customers to get request
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/allcustomers")
	public ResponseEntity<String> getCustomerList(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);

			if (validateUser(JSONBody)) {
				return ResponseEntity.ok(getAllRecords(RecordType.Customer).toString());
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Sends entire list of parts to get request
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/allparts")
	public ResponseEntity<String> getPartList(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);

			if (validateUser(JSONBody)) {
				return ResponseEntity.ok(getAllRecords(RecordType.Part).toString());
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Sends entire list of orders to get request
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/allorders")
	public ResponseEntity<String> getOrderList(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);

			if (validateUser(JSONBody)) {
				return ResponseEntity.ok(getAllRecords(RecordType.Order).toString());
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}


	/**
	 * Sends one customer to get request by ID
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/getcustomer")
	public ResponseEntity<String> getCustomer(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);
			JSONObject returnInfo = new JSONObject();
			if (validateUser(JSONBody)) {
				CustomerRecord info = DBInterfacer.getCustomerRecord(JSONBody.getInt("id"));
				if (info == null) {
					return ResponseEntity.ok("User ID does not exist!");
				} else {
					returnInfo.put("customer", info.toJSONObject());
					return ResponseEntity.ok(returnInfo.toString());
				}
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Sends one part to get request by ID
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/getpart")
	public ResponseEntity<String> getPart(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);
			JSONObject returnInfo = new JSONObject();
			if (validateUser(JSONBody)) {
				PartRecord info = DBInterfacer.getPartRecord(JSONBody.getInt("id"));
				if (info == null) {
					return ResponseEntity.ok("User ID does not exist!");
				} else {
					returnInfo.put("part", info.toJSONObject());
					return ResponseEntity.ok(returnInfo.toString());
				}
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}

	/**
	 * Sends one order to get request by ID
	 * 
	 * @param body
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/api/getorder")
	public ResponseEntity<String> getOrder(@RequestBody String body) {
		try {
			JSONObject JSONBody = new JSONObject(body);
			JSONObject returnInfo = new JSONObject();
			if (validateUser(JSONBody)) {
				PartRecord info = DBInterfacer.getPartRecord(JSONBody.getInt("id"));
				if (info == null) {
					return ResponseEntity.ok("User ID does not exist!");
				} else {
					returnInfo.put("part", info.toJSONObject());
					return ResponseEntity.ok(returnInfo.toString());
				}
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}

	
//	@PostMapping("/api/addcustomer")
//	public ResponseEntity<String> addCustomer(@RequestBody String body){
//		try {
//			JSONObject JSONBody = new JSONObject(body);
//			if (validateUser(JSONBody)) {
//				CustomerRecord info = DBInterfacer.getCustomerRecord(JSONBody.getInt("id"));
//				if (info == null) {
//					return ResponseEntity.ok("Customer ID does not exist!");
//				} else {
//					info.updateFromJSONObjcet(JSONBody);
//					DBInterfacer.insert(info);
//					return ResponseEntity.ok("Customer information updated");
//				}
//				
//			} else {
//				return ResponseEntity.ok("Invalid username or password");
//			}
//		} catch (JSONException e) {
//			return ResponseEntity.ok(e.toString());
//		}
//	}
	
	/**
	 * Edits customer record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/editcustomer")
	public ResponseEntity<String> changeCustomer(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				CustomerRecord info = DBInterfacer.getCustomerRecord(JSONBody.getInt("id"));
				if (info == null) {
					return ResponseEntity.ok("Customer ID does not exist!");
				} else {
					info.updateFromJSONObjcet(JSONBody);
					DBInterfacer.update(info);
					return ResponseEntity.ok("Customer information updated");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Edits part record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/editpart")
	public ResponseEntity<String> changePart(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				PartRecord info = DBInterfacer.getPartRecord(JSONBody.getInt("id"));
				if (info == null) {
					return ResponseEntity.ok("Part number does not exist!");
				} else {
					info.updateFromJSONObjcet(JSONBody);
					DBInterfacer.update(info);
					return ResponseEntity.ok("Part information updated");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Changes shipping costs
	 * @param body
	 * @return
	 */
	@PostMapping("/api/updateshipping")
	public ResponseEntity<String> updateShipping(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("weight") && JSONBody.has("cost")) {
					AdminPageController.setCost(JSONBody.getDouble("cost"));
					AdminPageController.setThreshold(JSONBody.getDouble("weight"));
					return ResponseEntity.ok("Updated costs");
				} else {
					return ResponseEntity.ok("Request does not have a weight or cost");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * get shipping costs
	 * @param body
	 * @return
	 */
	@GetMapping("/api/getshipping")
	public ResponseEntity<String> getShipping(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				JSONObject data = new JSONObject();
				data.put("weight", AdminPageController.getThreshold());
				data.put("cost", AdminPageController.getCost());
				return ResponseEntity.ok(data.toString()); 
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	

	/**
	 * deletes customer record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/deletecustomer")
	public ResponseEntity<String> deleteCustomer(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("id")) {
					if(deleteRecord(RecordType.Customer, JSONBody.getInt("id"))) {
						return ResponseEntity.ok("Customer has been removed");
					} else {

						return ResponseEntity.ok("Unable to remove customer");
					}
				} else {
					return ResponseEntity.ok("Request does not have a customer ID");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * deletes part record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/deletepart")
	public ResponseEntity<String> deletePart(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("number")) {
					if(deleteRecord(RecordType.Part, JSONBody.getInt("number"))) {
						return ResponseEntity.ok("Part has been removed");
					} else {

						return ResponseEntity.ok("Unable to remove part");
					}
				} else {
					return ResponseEntity.ok("Request does not have a part number");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * deletes order record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/deleteorder")
	public ResponseEntity<String> deleteOrder(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("id")) {
					if(deleteRecord(RecordType.Order, JSONBody.getInt("id"))) {
						return ResponseEntity.ok("Order has been removed");
					} else {

						return ResponseEntity.ok("Unable to remove order");
					}
				} else {
					return ResponseEntity.ok("Request does not have a order id");
				}
				
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * adds customer record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/createcustomer")
	public ResponseEntity<String> createCustomer(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("contact") && 
						JSONBody.has("name") &&
						JSONBody.has("city") && 
						JSONBody.has("street")) {
					CustomerRecord newRecord = new CustomerRecord(
							CheckoutController.getOpenCustomerID(),
							JSONBody.getString("name"),
							JSONBody.getString("city"),
							JSONBody.getString("street"),
							JSONBody.getString("contact")
							);
					DBInterfacer.insert(newRecord);
					return ResponseEntity.ok("Created customer");
				} else {
					return ResponseEntity.ok("Request does not have the correct fields");
				}
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * adds customer record
	 * @param body
	 * @return
	 */
	@PostMapping("/api/createpart")
	public ResponseEntity<String> createPart(@RequestBody String body){
		try {
			JSONObject JSONBody = new JSONObject(body);
			if (validateUser(JSONBody)) {
				if (JSONBody.has("description") && 
						JSONBody.has("price") &&
						JSONBody.has("weight") && 
						JSONBody.has("pictureURL") &&
						JSONBody.has("quantity")) {
					PartRecord newRecord = new PartRecord(
							CheckoutController.getOpenPartNumber(),
							JSONBody.getString("description"),
							JSONBody.getDouble("price"),
							JSONBody.getDouble("weight"),
							JSONBody.getString("pictureURL"),
							JSONBody.getInt("quantity")
							);
					DBInterfacer.insert(newRecord);
					return ResponseEntity.ok("Created part");
				} else {
					return ResponseEntity.ok("part does not have the correct fields");
				}
			} else {
				return ResponseEntity.ok("Invalid username or password");
			}
		} catch (JSONException e) {
			return ResponseEntity.ok(e.toString());
		}
	}
	
	/**
	 * Validate username and password of the request
	 * 
	 * @param data JSONObject of the body of the request
	 * @return true if valid user
	 */
	private boolean validateUser(JSONObject data) throws JSONException {
		return data.getString("user").equals("admin") && data.getString("password").equals("password");
	}
	
	private boolean deleteRecord(RecordType type, int recordId) {
		if(type == RecordType.Part) {
			PartRecord record = DBInterfacer.getPartRecord(recordId);
			if(record != null) {
				DBInterfacer.delete(record);
				return true;
			} else {
				return false;
			}
		} else if(type == RecordType.Customer) {
			CustomerRecord record = DBInterfacer.getCustomerRecord(recordId);
			if(record != null) {
				DBInterfacer.delete(record);
				return true;
			} else {
				return false;
			}
		} else {
			OrderRecord record = DBInterfacer.getOrderRecord(recordId);
			if(record != null) {
				DBInterfacer.delete(record);
				return true;
			} else {
				return false;
			}
		}
	}

	private JSONObject getAllRecords(RecordType type) {
		JSONObject returnInfo = new JSONObject();
		try {
			if (type == RecordType.Part) {
				LinkedList<PartRecord> records = DBInterfacer.getAllPartRecords();
				returnInfo.put("count", records.size());
				JSONArray partArray = new JSONArray();
				for (PartRecord record : records) {
					partArray.put(record.toJSONObject());
				}
				returnInfo.put("part-list", partArray);
			} else if(type == RecordType.Customer) {
				LinkedList<CustomerRecord> records = DBInterfacer.getAllCustomerRecords();
				returnInfo.put("count", records.size());
				JSONArray recordArray = new JSONArray();
				for (CustomerRecord record : records) {
					recordArray.put(record.toJSONObject());
				}
				returnInfo.put("customer-list", recordArray);
			} else {
				LinkedList<OrderRecord> records = DBInterfacer.getAllOrderRecords();
				returnInfo.put("count", records.size());
				JSONArray recordArray = new JSONArray();
				for (OrderRecord record : records) {
					recordArray.put(record.toJSONObject());
				}
				returnInfo.put("order-list", recordArray);
			}

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			Date date = new Date();

			returnInfo.put("request-time", formatter.format(date));
		} catch (JSONException e) {

		}
		return returnInfo;
	}
}