from django import forms
from .models import *
from django.forms import formset_factory

class contact_form(forms.ModelForm):
    class Meta:
        model = contact_master
        fields = ['fname', 'lname', 'mname']

'''
class address_form(forms.ModelForm):
    class meta:
        #model = address_master
        #fields = ['address_type', 'address', 'city', 'state', 'zip']
        model = phone_master
        fields = ['phone_id', 'phone_type', 'area_code', 'number']
'''

class address(forms.ModelForm):
    class Meta:
        model = address_master
        fields = ['address_type', 'address', 'city', 'state', 'zip']

class phone_form(forms.ModelForm):
    class Meta:
        model = phone_master
        fields = ['phone_type', 'area_code', 'number']

class date_form(forms.ModelForm):
    class Meta:
        model = date_master
        fields = ['date_type', 'date']

contact_form_set = formset_factory(contact_form)
address_form_set = formset_factory(address)
phone_form_set = formset_factory(phone_form)
date_form_set = formset_factory(date_form)


'''
class contact_form(forms.ModelForm):
    class Meta:
        model = contact_master
        fields = ['fname', 'lname', 'mname']

address_form_set = formset_factory(address_form)
phone_form_set = formset_factory(phone_form)
date_form_set = formset_factory(date_form)


#address_form_set = inlineformset_factory(contact_master, address_master, form = contact_form, fields = ['address_type', 'address'])
#phone_form_set = inlineformset_factory(contact_master, phone_master, form = contact_form, fields = ['phone_type', 'area_code'])
#date_form_set = inlineformset_factory(contact_master, date_master, form = contact_form, fields = ['date_type', 'date'])

'''