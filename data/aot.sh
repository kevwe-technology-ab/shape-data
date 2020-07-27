#! /bin/bash -x

jaotc --info --output metadao-api-1.0.4.so --jar ./target/metadao-api-1.0.4-SNAPSHOT.jar
jaotc --compile-for-tiered --info --output metadao-1.0.4.so --search-path ./target --jar metadao-1.0.4-SNAPSHOT.jar
