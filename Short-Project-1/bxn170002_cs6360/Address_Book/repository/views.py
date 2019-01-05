from django.shortcuts import render, redirect
from .forms import *
import uuid
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse
import json
import random

# Create your views here.
def address_book_home(request):
    contacts = {}
    contact_master_object = contact_master.objects.all()

    for item in contact_master_object:
        contacts[str(item.contact_id)] = {
            'fname': item.fname,
            'lname': item.lname,
            'mname': item.mname,
            'phone':{
                i: {'phone_type': x.phone_type, 'area_code': x.area_code, 'number': x.number}  for i, x in enumerate(phone_master.objects.filter(contact_id = item))
            },
            'address' : {i: {'address_type': x.address_type, 'address': x.address, 'city': x.city, 'state': x.state, 'zip': x.zip}  for i, x in enumerate(address_master.objects.filter(contact_id = item))},
        }

    return render(
        request, 'repository/home.html',
        context = {'contacts': contacts}
    )

# Add new contacts
def extract_data(request, field_names):
    # data store
    data_load = {}
    # utility variables
    i = 0

    form_id = 'form-'

    string = [(form_id + str(i) + '-' + x) for x in field_names]

    while (request.POST.get(string[0])):
        temp = {}
        for item in string:
            temp[item] = request.POST.get(item)
        data_load[i] = temp
        i += 1
        string = [(form_id + str(i) + '-' + x) for x in field_names]

    return data_load

@csrf_exempt
def add_new_contact(request):
    if request.method == 'POST':
        contact_fs = contact_form_set(request.POST)
        phone_fs = phone_form_set(request.POST)
        address_fs = address_form_set(request.POST)
        date_fs = date_form_set(request.POST)

        if contact_fs.is_valid() and phone_fs.is_valid() \
            and address_fs.is_valid(): #and date_fs.is_valid():
            contact_info = contact_fs.cleaned_data

            # form field variables
            phone_fields = ['phone_type', 'area_code', 'number']
            address_fields = ['address_type', 'address', 'city', 'state', 'zip']
            date_fields = ['date_type', 'date']

            # form extract data
            phone_info = extract_data(request, phone_fields)
            address_info = extract_data(request, address_fields)
            date_info = extract_data(request, date_fields)

            '''
                Model Objects
            '''

            # contact create
            uuid_field = random.randint(1, 10000)
            contact_master_object = contact_master(uuid_field, contact_info[0]['fname'], contact_info[0]['mname'], contact_info[0]['lname'])
            contact_master_object.save()

            contact_master_object = contact_master.objects.get(contact_id=uuid_field)

            # phone create
            phone_temp = []

            for index in range(len(phone_info)):
                for item in phone_fields:
                    phone_temp.append(phone_info[index]['form-' + str(index) + '-' + item])
                phone_master_object = phone_master(
                    contact_id=contact_master_object, phone_type=phone_temp[0],
                    area_code=phone_temp[1], number=phone_temp[2]
                )
                phone_master_object.save()
                print (phone_temp)
                del phone_temp[:]

            # address create
            address_temp = []
            for index in range(len(address_info)):
                for item in address_fields:
                    address_temp.append(address_info[index]['form-' + str(index) + '-' + item])
                address_master_object = address_master(
                    contact_id=contact_master_object, address_type=address_temp[0],
                    address=address_temp[1], city=address_temp[2],
                    state=address_temp[3], zip=address_temp[4]
                )
                address_master_object.save()
                print(address_temp)
                del address_temp[:]

            # date create
            date_temp = []
            for index in range(len(date_info)):
                for item in date_fields:
                    date_temp.append(date_info[index]['form-' + str(index) + '-' + item])
                date_master_object = date_master(
                    contact_id = contact_master_object, date_type = date_temp[0],
                    date = date_temp[1]
                )
                date_master_object.save()
                del date_temp[:]


        else:
            print (contact_fs.errors)
            print (phone_fs.errors)
            print (address_fs.errors)
            print (date_fs.errors)

    else:
        new_contact_form = contact_form()

    return render(
        request, 'repository/add_new_user.html',
        context={
            'contact_form': contact_form_set(),
            'address_form': address_form_set(),
            'phone_form': phone_form_set(),
            'date_form': date_form_set()
        },
    )

# View contacts

def view_all_contacts(request):
    contacts = {}
    contact_master_object = contact_master.objects.all()

    for item in contact_master_object:
        contacts[str(item.contact_id)] = {
            'fname': item.fname,
            'lname': item.lname,
            'mname': item.mname,
            'phone':{
                i: {'phone_type': x.phone_type, 'area_code': x.area_code, 'number': x.number}  for i, x in enumerate(phone_master.objects.filter(contact_id = item))
            },
            'address' : {i: {'address_type': x.address_type, 'address': x.address, 'city': x.city, 'state': x.state, 'zip': x.zip}  for i, x in enumerate(address_master.objects.filter(contact_id = item))},
            'date':{i: {'date_type': x.date_type, 'date': x.date}  for i, x in enumerate(date_master.objects.filter(contact_id = item))},
        }
    return render(
        request,
        'repository/all_contacts.html',
        context = {'contacts': contacts}
    )


def view_contact(request, user_id):
    contact = {}
    phone = {}
    address = {}
    date = {}
    contact_master_object = contact_master.objects.filter(contact_id = user_id)

    contact['fname'] = contact_master_object[0].fname
    contact['lname'] = contact_master_object[0].lname
    contact['mname'] = contact_master_object[0].mname

    phone_master_object = phone_master.objects.filter(contact_id = contact_master_object[0])
    for i, x in enumerate(phone_master_object):
        phone[i] = {
            'type': x.phone_type,
            'area_code': x.area_code,
            'number': x.number
        }

    address_master_object = address_master.objects.filter(contact_id = contact_master_object[0])
    for i, x in enumerate(address_master_object):
        address[i] = {
            'type': x.address_type,
            'address': x.address,
            'city': x.city,
            'state': x.state,
            'zip': x.zip
        }


    date_master_object = date_master.objects.filter(contact_id = contact_master_object[0])
    for i, x in enumerate(date_master_object):
        date[i] = {
            'type': x.date_type,
            'date': str(x.date)

        }
    return render(
        request, 'repository/view_contact.html',
        context = {
            'contact': contact,
            'address': address,
            'phone': phone,
            'date': date
        }
    )

def display_contacts(request, string):
    print (string.split(' '))
    contact_list = set()
    contacts = {}
    for item in string.split(' '):
        # Querying contact_master
        fname_list = contact_master.objects.filter(fname__icontains = item).values('contact_id')
        print (fname_list)
        for item in fname_list:
            contact_list.add(item['contact_id'])
        lname_list = contact_master.objects.filter(lname__icontains = item).values('contact_id')
        for item in lname_list:
            contact_list.add(item['contact_id'])
        mname_list = contact_master.objects.filter(mname__icontains = item).values('contact_id')
        for item in mname_list:
            contact_list.add(item['contact_id'])

        # Querying phone_master
        area_code_list = phone_master.objects.filter(area_code__icontains = item).values('contact_id')
        for item in area_code_list:
            contact_list.add(item['contact_id'])
        number_list = phone_master.objects.filter(number__icontains = item).values('contact_id')
        for item in number_list:
            contact_list.add(item['contact_id'])

        # Querying address_master
        address_list = address_master.objects.filter(address__icontains = item).values('contact_id')
        for item in address_list:
            contact_list.add(item['contact_id'])
        city_list = address_master.objects.filter(city__icontains = item).values('contact_id')
        for item in city_list:
            contact_list.add(item['contact_id'])
        state_list = address_master.objects.filter(state__icontains = item). values('contact_id')
        for item in state_list:
            contact_list.add(item['contact_id'])
        zip_list = address_master.objects.filter(zip__icontains = item). values('contact_id')
        for item in zip_list:
            contact_list.add(item['contact_id'])

        # Querying date_master
        date_list = date_master.objects.filter(date__icontains = item). values('contact_id')
        for item in date_list:
            contact_list.add(item['contact_id'])


    for item in list(contact_list):
        contact_temp = contact_master.objects.get(contact_id = item)
        contacts[str(contact_temp.contact_id)] = {
            'fname': contact_temp.fname,
            'lname': contact_temp.lname,
            'mname': contact_temp.mname,
            'phone':{
                i: {'phone_type': x.phone_type, 'area_code': x.area_code, 'number': x.number}  for i, x in enumerate(phone_master.objects.filter(contact_id = contact_temp))
            },
            'address' : {i: {'address_type': x.address_type, 'address': x.address, 'city': x.city, 'state': x.state, 'zip': x.zip}  for i, x in enumerate(address_master.objects.filter(contact_id = contact_temp))},
            'date':{i: {'date_type': x.date_type, 'date': x.date}  for i, x in enumerate(date_master.objects.filter(contact_id = contact_temp))},
        }

    return render(
        request,
        'repository/all_contacts.html',
        context = {'contacts': contacts}
    )



# Search contacts
@csrf_exempt
def search(request):
    if request.method == 'POST':
        search_string = request.POST.get('search_string')
        return redirect('display_contacts', string = search_string)
    return render(request, 'repository/search.html', )

def edit_form(request, contact_id):
    '''
    contact_master_object = contact_master.objects.get(contact_id = 234)
    phone_master_objects = phone_master.objects.filter(contact_id = contact_master_object).values('phone_type', 'area_code', 'number')
    address_master_objects = address_master.objects.filter(contact_id = contact_master_object).values('address_type', 'address', 'city', 'zip', 'state')
    date_master_objects = date_master.objects.filter(contact_id = contact_master_object).values('date_type', 'date')
    '''

    return redirect('admin/repository/contact_master/' + contact_id + '/change')

# Edit contacts
def edit_contacts(request, contact_id):
    return redirect('/admin/repository/contact_master/' + contact_id + '/change')

def delete_contacts(request, contact_id):
    contact_master.objects.filter(contact_id = contact_id).delete()

    contacts = {}
    contact_master_object = contact_master.objects.all()

    for item in contact_master_object:
        contacts[str(item.contact_id)] = {
            'fname': item.fname,
            'lname': item.lname,
            'mname': item.mname,
            'phone':{
                i: {'phone_type': x.phone_type, 'area_code': x.area_code, 'number': x.number}  for i, x in enumerate(phone_master.objects.filter(contact_id = item))
            },
            'address' : {i: {'address_type': x.address_type, 'address': x.address, 'city': x.city, 'state': x.state, 'zip': x.zip}  for i, x in enumerate(address_master.objects.filter(contact_id = item))},
            'date':{i: {'date_type': x.date_type, 'date': x.date}  for i, x in enumerate(date_master.objects.filter(contact_id = item))},
        }
    return render(
        request,
        'repository/all_contacts.html',
        context = {
            'contacts': contacts,
            'success_message': 'Contact successfully deleted!'
        }
    )


# Apis
def api_all_contacts(request):
    contacts = {}
    contact_master_object = contact_master.objects.all()

    for item in contact_master_object:
        contacts[str(item.contact_id)] = {
            'fname': item.fname,
            'lname': item.lname,
            'mname': item.mname,
            'phone':{
                i: {'phone_type': x.phone_type, 'area_code': x.area_code, 'number': x.number}  for i, x in enumerate(phone_master.objects.filter(contact_id = item))
            },
            'address' : {i: {'address_type': x.address_type, 'address': x.address, 'city': x.city, 'state': x.state, 'zip': x.zip}  for i, x in enumerate(address_master.objects.filter(contact_id = item))},
            'date':{i: {'date_type': x.date_type, 'date': x.date}  for i, x in enumerate(date_master.objects.filter(contact_id = item))},
        }
    contact_json = json.dumps(contacts, default = str)
    return JsonResponse(contacts)
