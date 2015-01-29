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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.plugins.annotations.Parameter;
import org.tomitribe.crest.xbean.ClasspathScanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@Mojo(name = "crest", requiresDependencyResolution = ResolutionScope.COMPILE, aggregator = true)
@Execute(phase = LifecyclePhase.COMPILE)
public class CrestMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}")
    private String targetDir;

    @Parameter(defaultValue = "commands.txt")
    private String classesCommandFileName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File commandClassFile = new File(targetDir, classesCommandFileName);

        ClasspathScanner classpathScanner = new ClasspathScanner();
        Iterator<Class<?>> iterator = classpathScanner.iterator();
        try (FileWriter fileWriter = new FileWriter(commandClassFile)) {
            while (iterator.hasNext()) {
                Class<?> clazz = iterator.next();
                fileWriter.append(clazz.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getClassesCommandFileName() {
        return classesCommandFileName;
    }

    public void setClassesCommandFileName(String classesCommandFileName) {
        this.classesCommandFileName = classesCommandFileName;
    }
}
