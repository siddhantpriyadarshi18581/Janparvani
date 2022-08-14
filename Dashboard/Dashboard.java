package org.bnmit.www.janaparvani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView speechBtn = findViewById(R.id.speechBtn);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Speechconversion.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "You Are Successfully Log out", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_support:
                Toast.makeText(this, "Support Page", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), Support.class);
                startActivity(intent2);
                break;
            case R.id.nav_SOS:
                Toast.makeText(this, "Mayday", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), SOS.class);
                startActivity(intent3);
                break;
            case R.id.nav_about:
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent4);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
