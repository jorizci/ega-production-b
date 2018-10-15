/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
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
 *
 */
package uk.ac.ebi.ega.file.re.encryption.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Batch {

    public static <T> List<List<T>> partition(Collection<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        List<T> batch = new ArrayList<>();
        for (T t : list) {
            batch.add(t);
            if (batch.size() == batchSize) {
                batches.add(batch);
                batch = new ArrayList<>();
            }
        }
        batches.add(batch);
        return batches;
    }

    public static <T, R> List<R> doInBatches(Collection<T> data, Function<List<T>, List<R>> function, int batchSize) {
        return partition(data, batchSize)
                .stream()
                .map(function)
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
    }

}
