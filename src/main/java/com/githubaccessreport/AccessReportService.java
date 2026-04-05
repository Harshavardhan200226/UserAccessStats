package com.githubaccessreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.*;

@Service
public class AccessReportService {

    private final String token;

    public AccessReportService(@org.springframework.beans.factory.annotation.Value("${github.token}") String token) {
        this.token = token;
    }

    public Map<String, Object> generateReport(String org) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        GHOrganization organization = github.getOrganization(org);

        Map<String, List<String>> userAccess = new HashMap<>();

        for (GHRepository repo : organization.listRepositories().toList()) {
            for (GHUser user : repo.listCollaborators().toList()) {
                String username = user.getLogin();
                userAccess.computeIfAbsent(username, k -> new ArrayList<>()).add(repo.getName());
            }
        }

        Map<String, Object> report = new HashMap<>();
        report.put("organization", org);
        report.put("userAccess", userAccess);
        return report;
    }

    }