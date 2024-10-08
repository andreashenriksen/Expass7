package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class User {

    private String username;
    private String email;

    private List<Poll> createdPolls;

    private Collection<Vote> allVotes;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdPolls = new ArrayList<>();
        this.allVotes = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Poll> getCreatedPolls() {
        return createdPolls;
    }

    public void deletePoll(Poll poll) {
        createdPolls.remove(poll);
    }

    public Collection<Vote> getAllVotes() {
        return allVotes;
    }

    public void deleteVote(Vote vote) {
        allVotes.remove(vote);
    }

}
