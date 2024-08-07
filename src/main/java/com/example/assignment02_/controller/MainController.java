package com.example.assignment02_.controller;

import com.example.assignment02_.model.Movie;
import com.example.assignment02_.service.ApiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import java.util.List;

public class MainController {
    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private ListView<String> movieListView;

    @FXML
    private Label averageRatingLabel;

    private final ApiService apiService = new ApiService();

    @FXML
    public void initialize() {
        genreComboBox.getItems().addAll("Action", "Comedy", "Drama");
        yearComboBox.getItems().addAll("2020", "2021", "2022");

        averageRatingLabel.setText("Average Rating: N/A");
    }

    @FXML
    public void onSearchMovies(ActionEvent event) {
        try {
            String genre = genreComboBox.getValue();
            String year = yearComboBox.getValue();
            List<Movie> movies = apiService.fetchMovies(genre, year);
            movieListView.getItems().clear();
            double totalRating = 0;
            int count = 0;

            for (Movie movie : movies) {
                movieListView.getItems().add(movie.getTitle() + " - Rating: " + movie.getRating());
                totalRating += movie.getRating();
                count++;
            }

            if (count > 0) {
                averageRatingLabel.setText("Average Rating: " + (totalRating / count));
            } else {
                averageRatingLabel.setText("Average Rating: N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
