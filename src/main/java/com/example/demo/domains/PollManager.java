package com.example.demo.domains;

import com.example.demo.models.Poll;
import com.example.demo.models.User;
import com.example.demo.models.Vote;
import com.example.demo.models.VoteOption;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class PollManager {

    private Map<String, User> userMap = new HashMap<>();
    private Map<Integer, Poll> pollMap = new HashMap<>();

    public PollManager() {
    }

    public User addUser(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    public User updateUser(String username, User updatedUser) {
        if (userMap.containsKey(username)) {
            updatedUser.setUsername(username);
            userMap.put(username, updatedUser);
            return updatedUser;
        }
        return null;
    }

    public boolean deleteUser(Integer userId) {
        return userMap.remove(userId) != null;
    }

    public Poll addPoll(Poll poll) {
        pollMap.put(poll.getId(), poll);
        return poll;
    }

    public Collection<Poll> getAllPolls() {
        return pollMap.values();
    }

    public Poll getPollById(Integer pollId) {
        return pollMap.get(pollId);
    }

    public Poll updatePoll(Integer pollId, Poll updatedPoll) {
        if (pollMap.containsKey(pollId)) {
            updatedPoll.setId(pollId);
            pollMap.put(pollId, updatedPoll);
            return updatedPoll;
        }
        return null;
    }

    public boolean deletePoll(Integer pollId) {
        return pollMap.remove(pollId) != null;
    }

    public void addVoteOption(Poll poll, VoteOption voteOption) {;
        poll.getVoteOptions().add(voteOption);
    }

    public void updateVoteOption(Poll poll, VoteOption updatedVoteOption) {
        for (VoteOption voteOption : poll.getVoteOptions()) {
            if (voteOption.getPoll().equals(poll)) {
                poll.getVoteOptions().remove(voteOption);
                poll.getVoteOptions().add(updatedVoteOption);
                updatedVoteOption.setPoll(poll);
                break;
            }
        }
    }

    public boolean deleteVoteOption(Poll poll) {
        for (VoteOption voteOption : poll.getVoteOptions()) {
            if (voteOption.getPoll().equals(poll)) {
                return poll.getVoteOptions().remove(voteOption);
            }
        }
        return false;
    }
    
    public void addVote(Vote vote, User user) {
        user.getAllVotes().add(vote);
    }

    public void updateVote(Vote updatedVote, User user) {
        for (Vote vote : user.getAllVotes()) {
            if (vote.getPoll().equals(updatedVote.getPoll())) {
                user.getAllVotes().remove(vote);
                user.getAllVotes().add(updatedVote);
                updatedVote.setPoll(pollMap.get(vote.getPoll().getId()));
                break;
            }
        }
    }

}
