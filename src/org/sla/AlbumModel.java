package org.sla;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class AlbumModel {
    // Class fields
    static private ArrayList<AlbumModel> albums;
    static private int currentAlbumNumber;

    // Object fields
    private int ranking;
    private String artist;
    private String name;
    private int year;
    private String genre;
    private float certifiedSales;
    private int claimedSales;

    // constructor
    AlbumModel(int ranking, String artist, String name, int year, String genre, float certifiedSales, int claimedSales) {
        this.ranking = ranking;
        this.artist = artist;
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.certifiedSales = certifiedSales;
        this.claimedSales = claimedSales;
    }

    // Class methods
    static void readAlbums() {
        if (albums != null) {
            // albums have already been read from file
            return;
        }

        // create array list class field where albums will be stored
        albums = new ArrayList<AlbumModel>();
        currentAlbumNumber = 0;

        String artist;
        try {
            // scan data file line-by-line
            File albumDataFile = new File("res/AlbumData");
            Scanner scanner = new Scanner(albumDataFile);
            int ranking = 1;
            while (scanner.hasNextLine()){
                String str = scanner.nextLine();
                Scanner lineScanner = new Scanner(str);
                lineScanner.useDelimiter("#");
                // scan data files line by separating text between #

                // first 4 data values are always present in each line
                artist = lineScanner.next();
                String album = lineScanner.next();
                int year = lineScanner.nextInt();
                String genre = lineScanner.next();
                // Some lines have certified sales value, then claimed sales value
                //  But some lines have only claimed sales value, no certified sales value
                // try reading first sales amount as a float (since certified sales value is a float)
                float certifiedSales;
                int claimedSales;
                float sales1 = lineScanner.nextFloat();
                if (lineScanner.hasNextInt()) {
                    // keep certified sales value as a float
                    certifiedSales = sales1;
                    // read claimed sales value as an int
                    claimedSales = lineScanner.nextInt();
                } else {
                    // convert float to integer since claimed sales values are all integers
                    claimedSales = (int)sales1;
                    // there is no certified sales value on this line
                    certifiedSales = 0;
                }

                AlbumModel newAlbum = new AlbumModel(ranking, artist, album, year, genre, certifiedSales, claimedSales);
                albums.add(newAlbum);
                ranking = ranking + 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String describeNextAlbum() {
        if (albums == null) {
            // read the albums from file
            readAlbums();
        }

        AlbumModel nextAlbum = albums.get(currentAlbumNumber);
        if (currentAlbumNumber < albums.size() - 1) {
            currentAlbumNumber = currentAlbumNumber + 1;
        } else {
            currentAlbumNumber = 0;
        }

        return nextAlbum.describe();
    }

    // Object getter/setters
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getCertifiedSales() {
        return certifiedSales;
    }

    public void setCertifiedSales(float sales) {
        this.certifiedSales = sales;
    }

    public int getClaimedSales() {
        return claimedSales;
    }

    public void setClaimedSales(int sales) {
        this.claimedSales = sales;
    }

    // Object methods
    public String describe() {
        String description = ranking + ". \"" + name + "\" by " + artist + " (released in " + year + ").\n";
        description = description + "     It's claimed to have sold " + claimedSales + " million copies";
        if (this.certifiedSales == 0) {
            description = description + ".\n";
        } else {
            description = description + ", but only " + certifiedSales + " million copies have been verified.\n";
        }
        return description;
    }
}
