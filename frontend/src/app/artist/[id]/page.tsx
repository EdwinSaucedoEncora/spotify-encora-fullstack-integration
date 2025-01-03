"use client";

import { useGetArtistQuery } from "@/services/spotify";
import { useParams, usePathname } from "next/navigation";
import { Suspense } from "react";

function Artist() {
  const { id } = useParams();
  const { data } = useGetArtistQuery(id);
  return <></>;
}

export default function ArtistPage() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Artist />
    </Suspense>
  );
}
