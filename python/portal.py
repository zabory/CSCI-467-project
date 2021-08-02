# This is a sample Python script.
from API.badcarpartsAPI import *
from tkinter import *
b = BadCarPartsApi()

root = Tk(className='BadCarParts Portal')
root.geometry("300x400")

def addInput(data,row=0,column=0):
    t = Label(root, text=data)
    t.grid(row=row, column=column)
    i = Entry(root)
    i.grid(row=row, column=column+1)
    return i

title = Label(root, text="Edit Customer")
title.grid(row=0,column=1)
cusData = [
    addInput("id",1,0),
    addInput("name",2,0),
    addInput("city",3,0),
    addInput("street",4,0),
    addInput("contact",5,0)
]
def get(in_data):
    return in_data.get()

def edit():
    if '' not in list(map(get,cusData)):
        id = int(cusData[0].get())
        #print(b.getId(BadCarPartsData.CUSTOMERS,id))
        b.set(BadCarPartsEdit.CUSTOMER, {"id": id, "name": cusData[1].get(), "city": cusData[2].get()
                                                 , "street": cusData[3].get(), "contact": cusData[4].get()})
        #print(b.getId(BadCarPartsData.CUSTOMERS,id))

def show():
    print(b.getString(BadCarPartsData.CUSTOMERS))

button = Button(root, text="Edit", command=edit)
button.grid(row=6,column=1)
button = Button(root, text="Show Customers", command=show)
button.grid(row=6,column=2)

root.mainloop()


exit(0)