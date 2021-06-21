package exporter.schema

final case class CustomField(
    gid: String,
    enabled: Boolean,
    name: String,
    number_value: Option[Int],
    precision: Int,
    created_by: CreatedByInfo,
    display_value: Option[String],
    resource_subtype: String,
    resource_type: String,
    `type`: String
)