from django.conf.urls import url, include
from .views import *

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    url('^all_contacts/$', view_all_contacts, name = 'view_all_contacts'),
    url('^api_all_contacts/$', api_all_contacts, name = 'api_all_contacts'),
    url('^add_new_contact/$', add_new_contact, name = 'add_new_contact'),
    url('^delete_contact/(?P<contact_id>[0-9]+)/$', delete_contacts, name = 'delete_contact'),
    url('^display_contact/(?P<string>[A-Za-z0-9-\w\s]+)/$', display_contacts, name = 'display_contacts'),
    url('^edit_contact/(?P<contact_id>[0-9]+)/$', edit_contacts, name = 'edit_contact'),
    url('^get_contact/(?P<user_id>[0-9]+)/$', view_contact, name = 'view_contact'),
    url('^search/$', search, name = 'search'),
    url('', address_book_home, name = 'home'),
    
    
]

