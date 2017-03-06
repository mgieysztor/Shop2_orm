package com.offcasoftware.shop2.view.widget;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;
import com.offcasoftware.shop2.view.ProductDetailsActivity;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author maciej.pachciarek on 2017-02-18.
 */

public class ProductCardView extends CardView {

    public interface ProductCardViewInterface {

        void onProductClicked(Product product);
    }

    @BindView(R.id.product_image)
    ImageView mProductImage;

    @BindView(R.id.product_name)
    TextView mProductName;

    @BindView(R.id.product_price)
    TextView mProductPrice;

    public ProductCardView(final Context context) {
        super(context);
        init();
    }

    public ProductCardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductCardView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void bindTo(final Product product,
            final ProductCardViewInterface productCardViewInterface) {
        int drawableResourceId = this.getResources().getIdentifier(product.getImageResId(),
                "drawable", getContext().getPackageName());
        mProductName.setText(product.getName());
        mProductPrice.setText(String.valueOf(product.getPrice()));
        mProductImage.setImageResource(drawableResourceId);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                productCardViewInterface.onProductClicked(product);
            }
        });
    }

    private void init() {
        inflate(getContext(), R.layout.view_product_card_view, this);
        ButterKnife.bind(this);

        Context context = getContext();
        int k = 0;
    }
}

