from django.db import models
import uuid

class contact_master(models.Model):
    contact_id = models.PositiveIntegerField(
        primary_key = True, 
    )
    fname = models.CharField(
        max_length = 100,
        help_text = "First Name of the user",
    )
    mname = models.CharField(
        max_length = 100,
        help_text = "Middle Name of the user",
        blank = True,
    )
    lname = models.CharField(
        max_length = 100,
        help_text = "Last Name of the user",
    )

    def __str__(self):
        return '%s, %s' %(self.lname, self.fname)

class address_master(models.Model):
    types_of_address = (
        ('home', 'Home'),
        ('work', 'Work'),
        
    )
    address_id = models.AutoField(primary_key=True)
    contact_id = models.ForeignKey(
        'contact_master', on_delete=models.CASCADE,
    )
    address_type = models.CharField(
        max_length = 4,
        choices = types_of_address,
        default = 'home'
    )
    address = models.CharField(
        max_length = 200,
        help_text = 'Address'
    )
    city = models.CharField(
        max_length = 50,
        help_text = 'City',
    )
    state = models.CharField(
        max_length = 20,
        help_text= 'State',
    )
    zip = models.CharField(
        max_length = 6,
        blank = True
    )

    def __str__(self):
        return '%s - %s' %(self.contact_id.fname, self.address)

class phone_master(models.Model):
    types_of_phone = (
        ('home', 'Home'),
        ('work', 'Work'),
        ('fax', 'Fax'),
        ('cell', 'Cell'),
    )
    phone_id = models.AutoField(primary_key=True)
    contact_id = models.ForeignKey(
        'contact_master', on_delete=models.CASCADE,
    )
    phone_type = models.CharField(
        max_length = 4,
        choices = types_of_phone,
        default = 'home'
    )
    area_code = models.CharField(
        max_length = 3,
        help_text = 'Area Code'
    )
    number = models.CharField(
        help_text = 'number',
        max_length = 8,
    )

    def __str__(self):
        return '%s - %s - %s' % (self.contact_id.contact_id, self.area_code, self.number)


class date_master(models.Model):
    types_of_dates = (
        ('bday', 'Birthday'),
        ('annv', 'Anniversary'),
    )
    date_id = models.AutoField(primary_key = True)
    contact_id = models.ForeignKey(
        'contact_master', on_delete = models.CASCADE,
    )
    date_type = models.CharField(
        max_length = 4,
        choices = types_of_dates,
        default = 'bday'
    )
    date = models.DateField()