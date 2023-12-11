package lagingoding.quizgame.ui.pyramid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lagingoding.quizgame.R;

public class PyramidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);

        //sesuaikan dengan jumlah bintang yg akan di tampilkan
        int jmlBintang = 10;

        TextView tvPyramid = findViewById(R.id.pyramid);
        tvPyramid.setText(generatePyramid(jmlBintang));
    }

    private String generatePyramid(int numRows) {
        StringBuilder pyramidOutput = new StringBuilder();

        for (int i = 1; i <= numRows; i++) {
            // Append spaces for alignment
            for (int j = 0; j < numRows - i; j++) {
                pyramidOutput.append("");
            }

            // Append asterisks for the current row
            for (int j = 0; j < i; j++) {
                pyramidOutput.append("* ");
            }

            // Append a newline character for the next row
            pyramidOutput.append("\n");
        }

        // Return the result as a String
        return pyramidOutput.toString();
    }
}