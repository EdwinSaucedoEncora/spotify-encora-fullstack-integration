"use client";
import { useSearchQuery } from "@/services/spotify";
import { useEffect } from "react";

export default function AlbumPage() {
  const {} = useSearchQuery("bon");
  useEffect(() => {});
  return <div>Album</div>;
}
