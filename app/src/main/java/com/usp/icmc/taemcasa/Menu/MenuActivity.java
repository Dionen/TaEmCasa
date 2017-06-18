package com.usp.icmc.taemcasa.Menu;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.MinhasVagas.MinhasVagasActivity;
import com.usp.icmc.taemcasa.Busca.SearchMenu;

import com.usp.icmc.taemcasa.Wishlist.WishlistActivity;

public class MenuActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_ID = 200;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String user_id;
    private String user_nome;
    private String user_sobrenome;
    private String user_email;
    ObjectAnimator animator;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        /* ORGANIZA AS ABAS */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags

        /* BOTAO DE NOTIFICAÇÃO */
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, "Tem pessoas interessadas em algumas de suas vagas!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                animator.setRepeatCount(1);

                Animation fadeout = new AlphaAnimation(1.f, 0.f);
                fadeout.setDuration(500);
                view.startAnimation(fadeout);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });

        /* ANIMAÇÃO DO BOTÃO */
        animator = ObjectAnimator.ofInt(fab, "backgroundTint", Color.rgb(255, 0, 0), Color.rgb(255, 255, 255), Color.rgb(255, 0, 0));
        animator.setDuration(1000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.setRepeatCount(Animation.INFINITE);
        animator.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                fab.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
            }
        });
        animator.start();

        /* RECEBE OS DADOS DO USUÁRIO */
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_nome = intent.getStringExtra("nome");
        user_sobrenome = intent.getStringExtra("sobrenome");
        user_email = intent.getStringExtra("email");

        Toast toast = Toast.makeText(MenuActivity.this, "Olá, " + user_nome + " " + user_sobrenome + "!\nSeu ID: " + user_id + "\nSeu email: " + user_email, Toast.LENGTH_LONG);
        toast.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* BARRA DE CONFIGURAÇÕES */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.sair_settings){
            AlertDialog alerta;
            /* BOTÃO DE SAIR */
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Log Off");
            builder.setMessage("Deseja realmente sair?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1){
                    SharedPreferences settings = getSharedPreferences("logged_user", Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    finish();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta = builder.create();
            alerta.show();
            return true;
        } else if (id == R.id.sobre_settings){
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    SearchMenu tab0 = new SearchMenu();
                    return tab0;
                case 1:
                    WishlistActivity tab1 = new WishlistActivity();
                    return tab1;
                case 2:
                    MinhasVagasActivity tab2 = new MinhasVagasActivity();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "BUSCAR VAGA";
                case 1:
                    return "WISHLIST";
                case 2:
                    return "MINHAS \nMORADIAS";
            }
            return null;
        }
    }

    /* NO MOMENTO FAZ LOG-OFF PELO BOTÃO VOLTAR */
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
