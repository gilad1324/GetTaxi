package com.jct.gilad.gettaxi.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jct.gilad.gettaxi.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private PlaceAutocompleteFragment placeAutocompleteFragment1;
    private PlaceAutocompleteFragment placeAutocompleteFragment2;
    Location locationA = new Location("A");//= new Location(from);
    Location locationB = new Location("B");//= new Location(to);

    //private EditText IdEditText;
    private EditText NameEditText;
    private EditText PhoneEditText;
    private EditText EmailEditText;
    private TextInputLayout inputLayoutName, inputLayoutPhone, inputLayoutEmail;
    private FloatingActionButton addTaxiButton;

    private FloatingActionButton getLocationButton;
    private FloatingActionButton stopUpdateButton;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        findViewsLocationSearch();
        findViewsGPS();
    }

    private void findViews() {
        ///IdEditText = (EditText)findViewById( R.id.IdEditText );
        NameEditText = (EditText) findViewById(R.id.NameEditText);
        PhoneEditText = (EditText) findViewById(R.id.PhoneEditText);
        EmailEditText = (EditText) findViewById(R.id.EmailEditText);

        addTaxiButton = (FloatingActionButton) findViewById(R.id.addTaxiButton);
        //////////
        addTaxiButton.setOnClickListener(this);
        //////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);

        NameEditText.addTextChangedListener(new MyTextWatcher(NameEditText));
        PhoneEditText.addTextChangedListener(new MyTextWatcher(PhoneEditText));
        EmailEditText.addTextChangedListener(new MyTextWatcher(EmailEditText));

        addTaxiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validateName()) {
            Toast.makeText(getApplicationContext(), R.string.err_msg_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePhone()) {
            Toast.makeText(getApplicationContext(), R.string.err_msg_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail()) {
            Toast.makeText(getApplicationContext(), R.string.err_msg_email, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (NameEditText.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(NameEditText);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = EmailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(EmailEditText);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        String phone = PhoneEditText.getText().toString().trim();

        if (phone.isEmpty() || !isValidPhone(phone)) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(PhoneEditText);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.NameEditText:
                    validateName();
                    break;
                case R.id.EmailEditText:
                    validateEmail();
                    break;
                case R.id.PhoneEditText:
                    validatePhone();
                    break;
            }
        }
    }

    private void findViewsLocationSearch() {
        placeAutocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        placeAutocompleteFragment1.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);

        placeAutocompleteFragment1.setHint(getString(R.string.hint_source));
        placeAutocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        placeAutocompleteFragment2.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
        placeAutocompleteFragment2.setHint(getString(R.string.hint_dest));
        placeAutocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationA.setLatitude(place.getLatLng().latitude);
                locationA.setLongitude(place.getLatLng().longitude);
                // .getAddress().toString();//get place details here
            }

            @Override
            public void onError(Status status) {

            }
        });

        placeAutocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //  to = place.getAddress().toString();//get place details here
                locationB.setLatitude(place.getLatLng().latitude);
                locationB.setLongitude(place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    private void findViewsGPS() {
        getLocationButton = (FloatingActionButton) findViewById(R.id.getLocationButton);
        getLocationButton.setOnClickListener(this);

        stopUpdateButton = (FloatingActionButton) findViewById(R.id.stopUpdateButton);
        stopUpdateButton.setOnClickListener(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //    Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                placeAutocompleteFragment1.setText(getPlace(location));////location.toString());
                stopUpdateButton.setImageResource(R.drawable.ic_mylocation);
                // Remove the listener you previously added
                //  locationManager.removeUpdates(locationListener);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }

    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            stopUpdateButton.setEnabled(true);
            getLocationButton.setEnabled(false);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

    }


    public String getPlace(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                //  String stateName = addresses.get(0).getAddressLine(1);
                //  String countryName = addresses.get(0).getAddressLine(2);
                //  return stateName + "\n" + cityName + "\n" + countryName;
                return cityName;
            }

            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                stopUpdateButton.setEnabled(true);
                getLocationButton.setEnabled(false);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == addTaxiButton)
            addTaxi();
        if (v == getLocationButton) {
            getLocation(); // Handle clicks for getLocationButton
            getLocationButton.setEnabled(false);
            stopUpdateButton.setEnabled(true);
            getLocationButton.setVisibility(View.INVISIBLE);
            stopUpdateButton.setVisibility(View.VISIBLE);
        }
        if (v == stopUpdateButton) {
            // Remove the listener you previously added
            locationManager.removeUpdates(locationListener);
            stopUpdateButton.setEnabled(false);
            getLocationButton.setEnabled(true);
            stopUpdateButton.setVisibility(View.INVISIBLE);
            getLocationButton.setVisibility(View.VISIBLE);
            stopUpdateButton.setImageResource(R.drawable.ic_mylocation_searching);
        }
    }

    private void addTaxi() {
        try {
            //int id = Integer.valueOf(this.IdEditText.getText().toString());
            String clientName;
            long clientPhoneNumber;
            String clientEmail;
            if (EmailEditText.getText().toString().trim().length() > 0) {
                clientEmail = EmailEditText.getText().toString();
            } else//do somting with tost

                if (NameEditText.getText().toString().trim().length() > 0) {
                    clientName = NameEditText.getText().toString();
                } else//do somting with tost

                    if (PhoneEditText.getText().toString().trim().length() > 0) {
                        clientPhoneNumber = Long.parseLong(PhoneEditText.getText().toString());
                    }
            //else//do somting with tost

            //Status status= Status.AVAILABLE;

            // Location sourceLocation
            //Location destLocation,
            //Time startTime,
            //Time endTime,


            //DBManagerFactory.getManager().addLecturer(contentValues);
        } catch (Exception e) {

        }
    }
}