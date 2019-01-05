# Generated by Django 2.1.1 on 2018-10-14 19:42

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='address_master',
            fields=[
                ('address_id', models.AutoField(primary_key=True, serialize=False)),
                ('address_type', models.CharField(choices=[('home', 'Home'), ('work', 'Work')], default='home', max_length=4)),
                ('address', models.CharField(help_text='Address', max_length=200)),
                ('city', models.CharField(help_text='City', max_length=50)),
                ('state', models.CharField(help_text='State', max_length=20)),
                ('zip', models.CharField(blank=True, max_length=6)),
            ],
        ),
        migrations.CreateModel(
            name='contact_master',
            fields=[
                ('contact_id', models.PositiveIntegerField(primary_key=True, serialize=False)),
                ('fname', models.CharField(help_text='First Name of the user', max_length=100)),
                ('mname', models.CharField(blank=True, help_text='Middle Name of the user', max_length=100)),
                ('lname', models.CharField(help_text='Last Name of the user', max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='date_master',
            fields=[
                ('date_id', models.AutoField(primary_key=True, serialize=False)),
                ('date_type', models.CharField(choices=[('bday', 'Birthday'), ('annv', 'Anniversary')], default='bday', max_length=4)),
                ('date', models.DateField()),
                ('contact_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='repository.contact_master')),
            ],
        ),
        migrations.CreateModel(
            name='phone_master',
            fields=[
                ('phone_id', models.AutoField(primary_key=True, serialize=False)),
                ('phone_type', models.CharField(choices=[('home', 'Home'), ('work', 'Work'), ('fax', 'Fax'), ('cell', 'Cell')], default='home', max_length=4)),
                ('area_code', models.CharField(help_text='Area Code', max_length=3)),
                ('number', models.CharField(help_text='number', max_length=8)),
                ('contact_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='repository.contact_master')),
            ],
        ),
        migrations.AddField(
            model_name='address_master',
            name='contact_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='repository.contact_master'),
        ),
    ]
