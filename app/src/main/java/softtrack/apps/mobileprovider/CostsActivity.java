package softtrack.apps.mobileprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CostsActivity extends AppCompatActivity {

    public LinearLayout activityCostsContainerCalls;
    public LinearLayout activityCostsContainerMessages;
    public LinearLayout activityCostsContainerDateUsage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        initialize();

    }

    public void initialize() {
        findViews();
        addHandlers();
        initializeActionBar();
    }

    public void findViews() {
        activityCostsContainerCalls = findViewById(R.id.activity_costs_container_calls);
        activityCostsContainerMessages = findViewById(R.id.activity_costs_container_messages);
        activityCostsContainerDateUsage = findViewById(R.id.activity_costs_container_data_usage);
    }

    public void addHandlers() {
        activityCostsContainerCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CostsActivity.this, DetalizationActivity.class);
                intent.putExtra("data", "calls");
                CostsActivity.this.startActivity(intent);
            }
        });
        activityCostsContainerMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CostsActivity.this, DetalizationActivity.class);
                intent.putExtra("data", "messages");
                CostsActivity.this.startActivity(intent);
            }
        });
        activityCostsContainerDateUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CostsActivity.this, DetalizationActivity.class);
                intent.putExtra("data", "dataUsage");
                CostsActivity.this.startActivity(intent);
            }
        });
    }

    public void initializeActionBar() {
        setTitle("Расходы");
    }

}
