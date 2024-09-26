// Common

variable "service_account_key_file" {
  type    = string
  default = "./key.json"
}

variable "cloud_id" {
  type = string
  default = "b1grhl3koto4nt1cole1"
}

variable "folder_id" {
  type = string
  default = "b1g2he0fb1saj4amrkel"
}

variable "zone" {
  type    = string
  default = "ru-central1-b"
}

// Network

variable "network_name" {
  type = string
}

variable "v4_cidr_blocks" {
  type = list(string)
}

// VPC

variable "vm_name" {
  type = string
}

variable "image_id" {
  type = string
}

variable "ip_address" {
  type = string
}
