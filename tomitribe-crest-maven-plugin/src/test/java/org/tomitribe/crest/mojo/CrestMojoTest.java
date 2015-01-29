/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tomitribe.crest.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;
import org.tomitribe.crest.cmds.processors.Help;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class CrestMojoTest {

    private static final String CLASSES_COMMAND_FILE_NAME = "commands.txt";
    private static final String TARGET = "target";

    @Test
    public void testExecuteMethod() throws MojoFailureException, MojoExecutionException, IOException {
        File commands = new File(TARGET, CLASSES_COMMAND_FILE_NAME);
        commands.deleteOnExit();

        CrestMojo crestMojo = new CrestMojo();
        crestMojo.setTargetDir(TARGET);
        crestMojo.setClassesCommandFileName(CLASSES_COMMAND_FILE_NAME);
        crestMojo.execute();

        assertTrue(commands.exists());

        List<String> lines = Files.readAllLines(commands.toPath());
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
        assertEquals(Help.class.getName(), lines.get(0));
    }
}
