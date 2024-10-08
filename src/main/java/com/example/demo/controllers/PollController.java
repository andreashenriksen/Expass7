package com.example.demo.controllers;

import com.example.demo.domains.PollManager;
import com.example.demo.models.Poll;
import com.example.demo.models.User;
import com.example.demo.models.Vote;
import com.example.demo.models.VoteOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollManager pollManager;

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Map<String, Object> request) {
        int id = Integer.parseInt(request.get("id").toString());
        String username = request.get("username").toString();
        String question = request.get("question").toString();
        List<String> options = List.of(request.get("options").toString().split(","));
        System.out.println(options);
        User user = pollManager.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        Poll poll = new Poll(id, question, user);
        for (String option : options) {
            new VoteOption(option, poll);
        }
        Poll createdPoll = pollManager.addPoll(poll);
        return ResponseEntity.ok(createdPoll);
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Poll> votePoll(@PathVariable int id, @RequestBody Map<String, Object> request) {
        User user = pollManager.getUserByUsername(request.get("username").toString());
        Poll poll = pollManager.getPollById(id);
        String option = (String) request.get("option");
        if (poll == null) {
            return ResponseEntity.badRequest().build();
        }

        VoteOption voteOption = poll.getVoteOptionByCaption(option);
        Vote vote = new Vote(user, poll, voteOption);
        user.getAllVotes().add(vote);
        return ResponseEntity.ok(poll);
    }

    @PutMapping("/{id}/vote/{voteId}")
    public ResponseEntity<Poll> votePoll(@PathVariable int id, @PathVariable int voteId, @RequestBody Map<String, Object> request) {
        User user = pollManager.getUserByUsername(request.get("username").toString());
        Poll poll = pollManager.getPollById(id);
        String option = (String) request.get("option");

        for (VoteOption voteOption : poll.getVoteOptions()) {
            for (Vote vote : voteOption.getVotes()) {
                if (vote.getUser().equals(user) && vote.getPoll().equals(poll)) {
                    user.getAllVotes().remove(vote);
                    voteOption.deleteVote(vote);
                    break;
                }
            }
        }
        VoteOption voteOption = poll.getVoteOptionByCaption(option);
        Vote vote = new Vote(user, poll, voteOption);
        user.getAllVotes().add(vote);
        return ResponseEntity.ok(poll);
    }

    @GetMapping
    public ResponseEntity<Collection<Poll>> getAllPolls() {
        return ResponseEntity.ok(pollManager.getAllPolls());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Integer id) {
        pollManager.getPollById(id).deletePoll();
        boolean deleted = pollManager.deletePoll(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
