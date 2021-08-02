import requests, json, enum

class BadCarPartsData(enum.Enum):
    CUSTOMERS = "allcustomers"
    CUSTOMER = "getcustomer"
    ORDERS = "allorders"
    ORDER = "getorder"
    PARTS = "allparts"
    PART = "getpart"

class BadCarPartsEdit(enum.Enum):
    CUSTOMER = "editcustomer"
    PART = "editpart"

class BadCarPartsApi:
    def __init__(self):
        self.site = "http://172.87.20.149:85/api/"
        self.access = { "user":"admin", "password":"password" }

    def getId(self,t:BadCarPartsData, id:int):
        self.access["id"] = str(id)
        if t == BadCarPartsData.PART or \
           t == BadCarPartsData.CUSTOMER or \
           t == BadCarPartsData.ORDER:
            return json.loads(requests.get(self.site + t.value, data=json.dumps(self.access)).content.decode('utf8'))
        return "error"

    def get(self,t:BadCarPartsData):
        if t == BadCarPartsData.PARTS or \
           t == BadCarPartsData.CUSTOMERS or \
           t == BadCarPartsData.ORDERS:
            return json.loads(requests.get(self.site + t.value, data=json.dumps(self.access)).content.decode('utf8'))
        return "error"

    def getIdString(self,t:BadCarPartsData,id:int):
        return json.dumps(self.getId(t,id), indent=4, sort_keys=True)

    def getString(self,t:BadCarPartsData):
        return json.dumps(self.get(t), indent=4, sort_keys=True)

    def set(self,t:BadCarPartsEdit,data:dict):
        for i in data:
            self.access[i] = data[i]
        requests.post(self.site + t.value, data=json.dumps(self.access))