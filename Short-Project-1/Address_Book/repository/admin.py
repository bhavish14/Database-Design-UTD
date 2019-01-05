from django.contrib import admin
from .models import *
# Register your models here.
#admin.site.register(contact_master)



class contact_master_inline(admin.TabularInline):
    model = contact_master

class productLengthInline(admin.TabularInline):
    model = address_master


class address_master_inline(admin.TabularInline):
    model = address_master

class phone_master_inline(admin.TabularInline):
    model = phone_master

class date_master_inline(admin.TabularInline):
    model = date_master

@admin.register(contact_master)
class contact_admin(admin.ModelAdmin):
    list_display = ('fname', 'mname', 'lname', 'contact_id')
    inlines = [address_master_inline, phone_master_inline, date_master_inline]
