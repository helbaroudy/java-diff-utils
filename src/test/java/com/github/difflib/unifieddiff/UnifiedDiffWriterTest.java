/*
 * Copyright 2019 java-diff-utils.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.difflib.unifieddiff;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

/**
 *
 * @author Tobias Warneke (t.warneke@gmx.net)
 */
public class UnifiedDiffWriterTest {

    public UnifiedDiffWriterTest() {
    }

    @Test
    public void testWrite() throws URISyntaxException, IOException {
        String str = readFile(UnifiedDiffReaderTest.class.getResource("jsqlparser_patch_1.diff").toURI(), Charset.defaultCharset());
        UnifiedDiff diff = UnifiedDiffReader.parseUnifiedDiff(new ByteArrayInputStream(str.getBytes()));

        StringWriter writer = new StringWriter();
        UnifiedDiffWriter.write(diff, writer);
        System.out.println(writer.toString());
    }

    static String readFile(URI path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
