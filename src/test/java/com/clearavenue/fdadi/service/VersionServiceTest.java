package com.clearavenue.fdadi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VersionServiceTest {

    @Autowired
    VersionService versionService;

    @Autowired
    BuildProperties buildProperties;

    @Test
    public void testVersion() {
        String expected = String.format("%s : %s", buildProperties.getArtifact(), buildProperties.getVersion());
        String actual = versionService.version();

        assertEquals(expected, actual);
    }
}
