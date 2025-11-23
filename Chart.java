package frontendpkg;

import backendpkg.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class InsightsChartFrame extends javax.swing.JFrame {

    private Database db;
    private CourseManagement cm;

    public InsightsChartFrame() {
        initComponents();
        loadCharts();
    }

    private void loadCharts() {
        loadStudentPerformanceChart();
        loadQuizAveragesChart();
        loadCompletionPercentagesChart();
    }

    private void loadStudentPerformanceChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JSONArray studentsData = getStudentsData();
        for (int i = 0; i < studentsData.length(); i++) {
            JSONObject student = studentsData.getJSONObject(i);
            String name = student.getString("name");
            int performance = student.getInt("performance");

            dataset.addValue(performance, "Student Performance", name);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Student Performance",
                "Student",
                "Score",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private void loadQuizAveragesChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JSONArray quizzesData = getQuizzesData();
        for (int i = 0; i < quizzesData.length(); i++) {
            JSONObject quiz = quizzesData.getJSONObject(i);
            String lesson = quiz.getString("lesson");
            double avgScore = quiz.getDouble("averageScore");

            dataset.addValue(avgScore, "Quiz Average", lesson);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Quiz Averages",
                "Lesson",
                "Average Score",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private void loadCompletionPercentagesChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JSONArray completionData = getCompletionData();
        for (int i = 0; i < completionData.length(); i++) {
            JSONObject lesson = completionData.getJSONObject(i);
            String lessonName = lesson.getString("lesson");
            double completionPercentage = lesson.getDouble("completionPercentage");

            dataset.addValue(completionPercentage, "Completion Percentage", lessonName);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Lesson Completion Percentages",
                "Lesson",
                "Completion Percentage",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private JSONArray getStudentsData() {
        try {
            FileReader reader = new FileReader("path_to_your_json_file/students.json");
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            return new JSONArray(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading student data: " + e.getMessage());
            return new JSONArray();
        }
    }

    private JSONArray getQuizzesData() {
        try {
            FileReader reader = new FileReader("path_to_your_json_file/quiz_data.json");
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            return new JSONArray(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading quiz data: " + e.getMessage());
            return new JSONArray();
        }
    }

    private JSONArray getCompletionData() {
        try {
            FileReader reader = new FileReader("path_to_your_json_file/completion_data.json");
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            return new JSONArray(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading completion data: " + e.getMessage());
            return new JSONArray();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instructor Insights");
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsightsChartFrame().setVisible(true);
            }
        });
    }
}