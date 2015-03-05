
# composer-maven-plugin

PHP Composer maven plugin for running composer.phar install or update actions for a PHP composer project.

Add this plugin as a plugin dependency to your Maven projects to install composer
dependencies found in a composer.json file

## Usage

Add the plugin to the <plugins> section within your pom.xml file.

```xml
<plugins>
    <plugin>
        <groupId>uk.co.codezen</groupId>
        <artifactId>composer-maven-plugin</artifactId>
        <version>1.0</version>

        <configuration>
            <phpPath>/usr/bin/php</phpPath>
            <composerPharPath>src/main/php/composer.phar</composerPharPath>
            <composerJsonPath>src/main/php/composer.json</composerJsonPath>
            <withDev>false</withDev>
            <withOptimisedAutoloader>true</withOptimisedAutoloader>
        </configuration>

        <executions>
            <execution>
                <phase>process-resources</phase>
                <goals>
                    <goal>install</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

## License

Copyright 2014 Simon Paulger <spaulger@codezen.co.uk>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
