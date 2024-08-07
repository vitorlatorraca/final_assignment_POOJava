package com.example.assignment02_.service;

import com.example.assignment02_.model.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String API_KEY = "512f6c5c7620fb7821612a4adebfa5f6";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public List<Movie> fetchMovies(String genre, String year) throws Exception {
        // Mapeamento dos gêneros para IDs da API
        String genreId;
        if ("Action".equals(genre)) {
            genreId = "28";
        } else if ("Comedy".equals(genre)) {
            genreId = "35";
        } else if ("Drama".equals(genre)) {
            genreId = "18";
        } else {
            genreId = "";
        }

        // Construindo a URL para a requisição
        String urlString = BASE_URL + "discover/movie?api_key=" + API_KEY + "&with_genres=" + genreId + "&primary_release_year=" + year;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Lendo a resposta da API
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        // Processando a resposta JSON
        String response = content.toString();
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");

        // Convertendo os resultados para objetos Movie
        Gson gson = new Gson();
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            JsonObject movieObject = results.get(i).getAsJsonObject();
            Movie movie = new Movie();
            movie.setTitle(movieObject.get("title").getAsString());
            movie.setRating(movieObject.get("vote_average").getAsDouble()); // Nota média
            movies.add(movie);
        }
        return movies;
    }
}
