from repository.models import *
import csv

source_handle = csv.reader(open('contacts.csv', 'r'))
next(source_handle, None)
for item in source_handle:
    contact_id = item[0]
    home_phone = item[4]
    cell_phone = item[5]
    home_address = item[6]
    home_city = item[7]
    home_state = item[8]
    home_zip = item[9]
    work_phone = item[10]
    work_address = item[11]
    work_city = item[12]
    work_state = item[13]
    work_zip = item[14]
    birth_date = item[15]
    contact_master_temp = contact_master.objects.get(contact_id = contact_id)
    if work_phone:
        t = work_phone.split('-')
        work_phone_temp = phone_master(contact_id = contact_master_temp, phone_type = 'work', area_code = t[0], number = t[1] + '-' + t[2])
        work_phone_temp.save()

    if cell_phone:
        t = cell_phone.split('-')
        cell_phone_temp = phone_master(contact_id = contact_master_temp, phone_type = 'cell', area_code = t[0], number = t[1] + '-' + t[2])
        cell_phone_temp.save()
    
    
    if home_phone:
        t = home_phone.split('-')
        home_phone_temp = phone_master(contact_id = contact_master_temp, phone_type = 'home', area_code = t[0], number = t[1] + '-' + t[2])
        home_phone_temp.save()
   
    


    if birth_date:
        date_temp = date_master(contact_id = contact_master_temp, date_type = 'bday', date = birth_date)
        date_temp.save()


    if home_address:
        home_address_temp = address_master(contact_id = contact_master_temp, address_type = 'home', address = home_address, city = home_city, state = home_state, zip = home_zip)
        home_address_temp.save()
    if work_address:
        work_address_temp = address_master(contact_id = contact_master_temp, address_type = 'work', address = work_address, city = work_city, state = work_state, zip = work_zip)
        work_address_temp.save()
    


    
    




for item in source_handle:
    contact_id = item[0]
    first_name = item[1]
    middle_name = item[2]
    last_name = item[3]
    home_phone = item[4]
    cell_phone = item[5]
    home_address = item[6]
    home_city = item[7]
    home_state = item[8]
    home_zip = item[9]
    work_phone = item[10]
    work_address = item[11]
    work_city = item[12]
    work_state = item[13]
    work_zip = item[14]
    birth_date = item[15]
    contact_master_temp = contact_master(contact_id, first_name, middle_name, last_name)
    contact_master_temp.save()