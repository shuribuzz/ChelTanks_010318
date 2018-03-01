package ru.alexandrpokh.cheltanks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ru.alexandrpokh.cheltanks.R;
import ru.alexandrpokh.cheltanks.fragment.HomeFragment;
import ru.alexandrpokh.cheltanks.fragment.LiveFragment;
import ru.alexandrpokh.cheltanks.fragment.StoreFragment;
import ru.alexandrpokh.cheltanks.fragment.ShedGamesFragment;
import ru.alexandrpokh.cheltanks.fragment.TeamFragment;
import ru.alexandrpokh.cheltanks.fragment.ContactsFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;

    // Индекс для определения текущего пункта меню навигации
    public static int navItemIndex = 0;

    // Теги, используемые для прикрепления фрагментов
    private static final String TAG_HOME = "home";
    private static final String TAG_LIVE = "live";
    private static final String TAG_TEAM = "team";
    private static final String TAG_SHED_GAMES = "shed_games";
    private static final String TAG_STORE = "store";
    private static final String TAG_CONTACTS = "contacts";
    public static String CURRENT_TAG = TAG_HOME;

    //Текст в тулбаре,показывающий выбранный пункт меню навигации
    private String[] activityTitles;

    // Флаг для загрузки HomeFragment пир нажатии BACK из другого фрагмента
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инициализация виджетов
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);

        // Загрузка титлов из ресусров в тулбар
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

/*
        // load nav menu header data
        loadNavHeader();
*/

        // Инициализация меню навигации
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Вывод фрагмента, который был выбран в меню навигации
    * */

    private void loadHomeFragment() {
        // Метод, определяющий какой конкретно пункт меню выбран
        selectNavMenu();

        // Метод, устанавливаюший заголовок в тулбар, какой фрагмент выбран
        setToolbarTitle();

        // Если снова выбран текущий пункт меню навигации, ничего не делаем
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Закрытие навигационного меню, после выбора нужного пункта
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    /**
     * Создание фрагментов в соответсвтии со значнием индекса фрагмента
     */
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                //team
                TeamFragment teamFragment = new TeamFragment();
                return teamFragment;

            case 2:
                // table
                ShedGamesFragment shedGamesFragment = new ShedGamesFragment();
                return shedGamesFragment;
            case 3:
                // live
                LiveFragment liveFragment = new LiveFragment();
                return liveFragment;
            case 4:
                // store
                StoreFragment storeFragment = new StoreFragment();
                return storeFragment;

            case 5:
                // contacts
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;

            default:
                return new HomeFragment();
        }
    }

    /**
     * Метод, устанавливаюший заголовок в тулбар, какой фрагмент выбран
     */
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    /**
     * Метод, определяющий какой конкретно пункт меню выбран
     */
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    /**
     * Инициализация меню навигации
     */
    private void setUpNavigationView() {
        //Установка слушателя на пункты навигационного меню
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // Метод, устанавливающий занчение CURRENT_TAG и navItemIndex
            // по нажатию соответсвующего пункта меню
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_team:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_TEAM;
                        break;
                    case R.id.nav_shed_games:
                        navItemIndex = 2;
                       CURRENT_TAG = TAG_SHED_GAMES;
                        break;
                    case R.id.nav_live:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LIVE;
                        break;
                    case R.id.nav_store:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_STORE;
                        break;
                    case R.id.nav_contacts:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_CONTACTS;
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    /**
     * Метод возвращает HomeFragment при нажатии BACK из другого фрагмента
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }
     
}

