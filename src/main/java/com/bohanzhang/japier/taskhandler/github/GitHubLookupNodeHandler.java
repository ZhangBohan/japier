package com.bohanzhang.japier.taskhandler.github;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.DefaultNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("github-lookup")
@Slf4j
public class GitHubLookupNodeHandler implements DefaultNodeHandler<User> {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User handle(Node node, NodeContext context) {
        String user = context.getString("user");
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        return restTemplate.getForObject(url, User.class);
    }
}
