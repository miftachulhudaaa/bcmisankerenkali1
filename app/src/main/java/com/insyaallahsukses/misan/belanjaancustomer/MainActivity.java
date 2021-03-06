package com.insyaallahsukses.misan.belanjaancustomer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView bgapp, imgPengguna;
    LinearLayout textsplash, menuNav, menus;
    Animation fromBottom, gambarMuncul;
    TextView tvNamaPengguna, tvEmailPengguna;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deklarasi layout drawer, karna sebagai layout utama di main
        setContentView(R.layout.activity_main_drawer);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        gambarMuncul = AnimationUtils.loadAnimation(this, R.anim.gambarmuncul);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        menus = (LinearLayout) findViewById(R.id.menus);
        menuNav = (LinearLayout) findViewById(R.id.menuNav);

        bgapp.animate().translationY(-1000).setDuration(900).setStartDelay(300);
        textsplash.animate().translationY(-500).setDuration(900).setStartDelay(300);

        menuNav.startAnimation(gambarMuncul);
        menus.startAnimation(fromBottom);

        //Navigation Drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Tombol icon menu drawer
        menuNav = (LinearLayout) findViewById(R.id.menuNav);
        menuNav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        //ambil navigationView dulu, baru set ke headerView
        View headerView = navigationView.getHeaderView(0);
        imgPengguna = headerView.findViewById(R.id.imgPengguna);
        tvNamaPengguna = headerView.findViewById(R.id.tvNamaPengguna);
        tvEmailPengguna = headerView.findViewById(R.id.tvEmailPengguna);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(imgPengguna);
            tvNamaPengguna.setText(user.getDisplayName());
            tvEmailPengguna.setText(user.getEmail());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(MainActivity.this, LoginPengguna.class));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_riwayatPesanan) {
            // Handle the camera action
        } else if (id == R.id.nav_informasiAkun) {

        } else if (id == R.id.nav_pemberitahuan) {

        } else if (id == R.id.nav_logOut) {
            mAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginPengguna.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
