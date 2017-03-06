package com.offcasoftware.shop2.view;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;
import com.offcasoftware.shop2.repository.ProductRepository;
import com.offcasoftware.shop2.repository.ProductRepositoryInterface;
import com.offcasoftware.shop2.view.widget.ProductCardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements ProductCardView.ProductCardViewInterface {

    @BindView(R.id.activity_main)
    View mRootLayout;

    @BindViews({R.id.product_1, R.id.product_2, R.id.product_3})
    List<ProductCardView> mProductCardViews;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.design_navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private ProductRepositoryInterface mProductRepository
            = ProductRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupActionBarDrawerToggle();
        setupNavigationView();
        setupBottomNavigationView();
        displayData();

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onProductClicked(final Product product) {
        Context context = this;
        Context context1 = getApplicationContext();
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(ProductDetailsActivity.INTENT_PRODUCT_ID, product.getId());
        startActivity(intent);

        Log.d("Shop", "Product clicked: " + product.getName());
    }

    @OnClick(R.id.add_new_product)
    public void onAddProductClicked(View view) {
        Snackbar mSnackbar = Snackbar.make(mRootLayout, "Brak internetu", Snackbar.LENGTH_LONG)
                .setAction("Odśwież", new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                        startActivity(intent);
                    }
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

        final View sbView = mSnackbar.getView();
        final TextView textView = (TextView)
                sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getColor(R.color.colorAccent));

        mSnackbar.show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Mój sklep");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void displayData() {
        List<Product> products = mProductRepository.getProducts();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            mProductCardViews.get(i).bindTo(product, this);
        }
    }

    private void setupActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                        R.string.drawer_open, R.string.drawer_close) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setupNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                        mDrawerLayout.closeDrawer(GravityCompat.START);

                        MainActivity.this.onNavigationItemSelected(item.getItemId());
                        return false;
                    }
                });
    }

    private void setupBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                        MainActivity.this.onNavigationItemSelected(item.getItemId());
                        return false;
                    }
                });
    }

    private void onNavigationItemSelected(@IdRes int menuId) {
        switch (menuId) {
            case R.id.action1:
                Toast.makeText(MainActivity.this, "Action1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action2:
                Toast.makeText(MainActivity.this, "Action2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action3:
                Toast.makeText(MainActivity.this, "Action3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
