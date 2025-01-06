"use client";
import { ArtistDescription } from "@/components/ui/artist";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { getSongLength } from "@/lib/duration";
import { useGetAlbumByIdQuery, useGetArtistQuery } from "@/services/spotify";
import Image from "next/image";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import { useMemo } from "react";

const getTotalDurationTracks = (tracks) => {
  const miliseconds = tracks.reduce(
    (acc, { duration_ms }) => acc + duration_ms,
    0
  );

  return getSongLength(miliseconds);
};

export default function AlbumPage() {
  const { id } = useParams();
  const router = useRouter();
  const { data, isLoading, isError } = useGetAlbumByIdQuery(id);

  const {
    images,
    name,
    release_date,
    total_tracks,
    release_date_precision,
    tracks,
    artists,
  } = data ?? {};
  const [image] = images ?? [];
  const [artist] = artists ?? [];

  const {
    data: artistData,
    isLoading: isLoadingArtist,
    isError: isErrorArtist,
  } = useGetArtistQuery(artist?.id ?? null);
  const { popularSongs, info, discography } = artistData ?? {};
  const { id: artistId, genres, popularity, type } = info ?? {};

  const year =
    release_date_precision !== "year"
      ? new Date(release_date).getFullYear()
      : release_date;

  const totalDuration = useMemo(
    () => getTotalDurationTracks(tracks?.items ?? []),
    [tracks]
  );
  return (
    <div className="p-4">
      <Button type="button" onClick={() => router.back()}>
        Go back
      </Button>
      {!isLoading && !isError && (
        <>
          <div className="flex gap-2 my-2 justify-center">
            <div className="max-w-64 flex items-center justify-center flex-col ">
              <Image
                className="rounded-lg"
                src={image?.url}
                width={image?.width}
                height={image?.height}
                alt={name + "_cover"}
              />
              <h1 className="text-center">{name}</h1>
              <div className="grid grid-cols-3 divide-x  *:text-center">
                <p>{year}</p>
                <p>{total_tracks} tracks</p>
                <p>{totalDuration}</p>
              </div>
            </div>
            {!isLoadingArtist && !isErrorArtist && (
              <ArtistDescription
                id={info.id}
                name={info.name}
                genres={genres}
                popularity={popularity}
                type={type}
                total={discography.total}
              />
            )}
          </div>
          <Table className="max-w-[64rem] mx-auto">
            <TableCaption>{name} songs list.</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">#</TableHead>
                <TableHead>Song Name</TableHead>
                <TableHead className="text-right">Song Length</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {tracks.items.map(({ id, name, album, duration_ms }, index) => {
                return (
                  <TableRow key={id}>
                    <TableCell className="font-medium">{index + 1}</TableCell>
                    <TableCell>{name}</TableCell>
                    <TableCell className="text-right">
                      {getSongLength(duration_ms)}
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </>
      )}
    </div>
  );
}
