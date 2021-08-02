from API.badcarpartsAPI import *

b = BadCarPartsApi()
print(b.getIdString(BadCarPartsData.PART,8))
print(b.getIdString(BadCarPartsData.CUSTOMER,18))
