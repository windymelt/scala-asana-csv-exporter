# scala-asana-csv-exporter

## Prerequieses

- `src/main/resources/application.conf`
  - `asana.pat`: Asana Personalized Access Token -- Retrive from here https://app.asana.com/0/developer-console
  - `asana.projectGid`: Project id -- Retrive from project permalink
  - `google.spreadsheetId`: Google Spreadsheet ID
  - `google.spreadsheetRange`: Range that this application writes into

## Run

```shell
% sbt run
```

You will see spreadsheet has been modified.
