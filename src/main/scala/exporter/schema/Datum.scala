package exporter.schema

import com.github.nscala_time.time.Imports.DateTime

// dataはArrayで返される。その要素の型
final case class Datum(
    gid: String,
    assignee: Option[AssigneeInfo],
    completed_at: Option[DateTime],
    created_at: DateTime,
    custom_fields: Seq[CustomField],
    due_at: Option[DateTime],
    due_on: Option[DateTime],
    modified_at: DateTime,
    name: String,
    notes: String,
    parent: Option[String],
    projects: Seq[ProjectInfo],
    start_on: Option[DateTime],
    tags: Seq[String]
)
