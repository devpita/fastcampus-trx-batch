#!/bin/sh

echo "Creating test database $3"

mysql -u"$1" -p"$2" -e "CREATE DATABASE $3"
mysql -u"$1" -p"$2" $3 < "src/test/resources/TrxBatchDbIntgTestData/SpringBatchSchema.sql"
mysql -u"$1" -p"$2" $3 < "src/test/resources/TrxBatchDbIntgTestData/ApplicationSchema.sql"
mysql -u"$1" -p"$2" $3 < "src/test/resources/TrxBatchDbIntgTestData/MonthlyTrxReportJobData.sql"

printf "Created test databse $3\n"
