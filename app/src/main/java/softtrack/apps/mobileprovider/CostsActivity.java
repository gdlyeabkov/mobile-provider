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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        initialize();

    }

    public void initialize() {
        findViews();
        addHandlers();
    }

    public void findViews() {
        activityCostsContainerCalls = findViewById(R.id.activity_costs_container_calls);
        activityCostsContainerMessages = findViewById(R.id.activity_costs_container_messages);
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
    }

}
